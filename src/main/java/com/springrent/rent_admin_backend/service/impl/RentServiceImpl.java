package com.springrent.rent_admin_backend.service.impl;

import com.springrent.rent_admin_backend.dto.rent.DetailRentDetailResponseDTO;
import com.springrent.rent_admin_backend.dto.rent.ListRentResponseDTO;
import com.springrent.rent_admin_backend.dto.rent.UpdateRentDetailItemRequestBodyDTO;
import com.springrent.rent_admin_backend.dto.rent.UpdateRentDetailRequestBodyDTO;
import com.springrent.rent_admin_backend.models.*;
import com.springrent.rent_admin_backend.models.enums.RentDetailState;
import com.springrent.rent_admin_backend.exception.DataNotExistsException;

import com.springrent.rent_admin_backend.repository.ProductRepository;
import com.springrent.rent_admin_backend.repository.RentDetailItemRepository;
import com.springrent.rent_admin_backend.repository.RentDetailRepository;
import com.springrent.rent_admin_backend.service.CustomerService;
import com.springrent.rent_admin_backend.service.ProductService;
import com.springrent.rent_admin_backend.service.RentService;
import com.springrent.rent_admin_backend.service.TotalPriceItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

import static com.springrent.rent_admin_backend.helpers.FilterDateParams.getDateTimestamp;
import static com.springrent.rent_admin_backend.mapper.RentMapper.*;

@AllArgsConstructor
@Service
public class RentServiceImpl implements RentService {
    private CustomerService customerService;
    private ProductService productService;
    private ProductRepository productRepository;
    private RentDetailRepository rentDetailRepository;
    private RentDetailItemRepository rentDetailItemRepository;

    RentDetail getRentById(Long rentId) throws DataNotExistsException {
        Optional<RentDetail> optionalRentDetail = rentDetailRepository.findByIdAndIsDeletedFalse(rentId);
        if (optionalRentDetail.isEmpty()) {
            throw new DataNotExistsException("Rent");
        }

        return optionalRentDetail.get();
    }

    List<UpdateRentDetailItemRequestBodyDTO> mapAllToSameProducts(
            List<UpdateRentDetailItemRequestBodyDTO> items
    ) {
        HashMap<Long, UpdateRentDetailItemRequestBodyDTO> mapRentDetailItemsDTO = new HashMap<>();
        for(UpdateRentDetailItemRequestBodyDTO item: items) {
            if (mapRentDetailItemsDTO.containsKey(item.getProductId())) {
                UpdateRentDetailItemRequestBodyDTO updateRentDetailItemRequestBodyDTO = mapRentDetailItemsDTO.get(item.getProductId());
                Integer latestQuantity = updateRentDetailItemRequestBodyDTO.getQuantity() + item.getQuantity();
                updateRentDetailItemRequestBodyDTO.setQuantity(latestQuantity);
                mapRentDetailItemsDTO.replace(item.getProductId(), updateRentDetailItemRequestBodyDTO);
            } else {
                mapRentDetailItemsDTO.put(item.getProductId(), item);
            }
        }

        return new ArrayList<>(mapRentDetailItemsDTO.values());
    }

    TotalPriceItem remapRentDetailItem(
            Users users,
            List<UpdateRentDetailItemRequestBodyDTO> items,
            Timestamp now
    ) throws DataNotExistsException{
        Double totalPriceRent = 0.0;
        List<RentDetailItem> rentDetailItems = new ArrayList<>();
        List<Product> adjustAvailableQuantityProducts = new ArrayList<>();
        for(UpdateRentDetailItemRequestBodyDTO updateRentDetailItemRequestBodyDTO: items) {
            Product product = productService.getProductById(updateRentDetailItemRequestBodyDTO.getProductId());

            RentDetailItem rentDetailItem = mapToRentDetailItem(updateRentDetailItemRequestBodyDTO, product, now);
            rentDetailItem.setUpdatedBy(users);
            rentDetailItem.setCreatedBy(users);

            Integer latestAvailableQuantity = product.getAvailableQuantity() - updateRentDetailItemRequestBodyDTO.getQuantity();
            product.setUpdatedAt(now);
            product.setAvailableQuantity(latestAvailableQuantity);

            adjustAvailableQuantityProducts.add(product);

            totalPriceRent += rentDetailItem.getTotalAmount();

            rentDetailItems.add(rentDetailItem);
        }

        productRepository.saveAll(adjustAvailableQuantityProducts);
        return new TotalPriceItem(totalPriceRent, rentDetailItems);
    }

    @Override
    public ListRentResponseDTO getListRent(
            Long customerId,
            Date startDate,
            Date endDate,
            String state
    ) {
        if (state == null) {
            state = RentDetailState.RENT.name();
        }


        Timestamp startTime = getDateTimestamp(startDate, -1);
        Timestamp endTime = getDateTimestamp(endDate, 1);

        List<RentDetailBasic> rentDetails;
        if (customerId == null) {
            rentDetails = rentDetailRepository.findByStateAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualAndIsDeletedFalse(
                    state,
                    startTime,
                    endTime
            );
        } else {
            rentDetails = rentDetailRepository.findByStateAndCustomer_IdAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualAndIsDeletedFalse(
                    state,
                    customerId,
                    startTime,
                    endTime
            );
        }

        return mapToListRentResponseDTO(rentDetails);
    }

    @Override
    public DetailRentDetailResponseDTO getRentDetailById(
            Long rentId
    ) throws DataNotExistsException {
        return mapToDetailRentResponseDTO(getRentById(rentId));
    }

    @Override
    @Transactional
    public DetailRentDetailResponseDTO addRentDetail(
            Users users,
            UpdateRentDetailRequestBodyDTO updateRentDetailRequestBodyDTO
    ) throws DataNotExistsException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Customer customer = customerService.getCustomerById(updateRentDetailRequestBodyDTO.getCustomerId());
        Address address = customerService.getAddressById(updateRentDetailRequestBodyDTO.getAddressId());

        updateRentDetailRequestBodyDTO.setItems(mapAllToSameProducts(updateRentDetailRequestBodyDTO.getItems()));

        TotalPriceItem totalPriceItems = remapRentDetailItem(users, updateRentDetailRequestBodyDTO.getItems(), now);

        RentDetail rentDetail = mapToRentDetail(customer, address, now);
        rentDetail.setCreatedAt(now);
        rentDetail.setTotalAmount(totalPriceItems.getTotalPriceRent());
        rentDetail.setCreatedBy(users);
        rentDetail.setUpdatedBy(users);

        rentDetail = rentDetailRepository.save(rentDetail);

        for(RentDetailItem rentDetailItem: totalPriceItems.getRentDetailItems()) {
            rentDetailItem.setRent(rentDetail);
        }

        List<RentDetailItem> items= rentDetailItemRepository.saveAll(totalPriceItems.getRentDetailItems());

        rentDetail.setRentDetailItems(items);

        return mapToDetailRentResponseDTO(rentDetail);
    }

    @Override
    @Transactional
    public void updateRentDetail(
            Users users,
            Long rentId,
            UpdateRentDetailRequestBodyDTO updateRentDetailRequestBodyDTO
    ) throws DataNotExistsException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        RentDetail rentDetail = getRentById(rentId);

        List<Product> adjustProductFromDelete = new ArrayList<>();

        for(RentDetailItem item: rentDetail.getRentDetailItems()) {
            if (!item.getIsDeleted()) {
                item.setIsDeleted(true);
                item.setUpdatedAt(now);
                item.setUpdatedBy(users);

                Product product = productService.getProductById(item.getProduct().getId());
                Integer latestProductAvailableQuantity = product.getAvailableQuantity() + item.getQuantity();
                product.setAvailableQuantity(latestProductAvailableQuantity);
                product.setUpdatedAt(now);
                adjustProductFromDelete.add(product);
            }
        }
        productRepository.saveAll(adjustProductFromDelete);
        rentDetailItemRepository.saveAll(rentDetail.getRentDetailItems());

        Customer customer = customerService.getCustomerById(updateRentDetailRequestBodyDTO.getCustomerId());
        Address address = customerService.getAddressById(updateRentDetailRequestBodyDTO.getAddressId());

        updateRentDetailRequestBodyDTO.setItems(mapAllToSameProducts(updateRentDetailRequestBodyDTO.getItems()));

        TotalPriceItem totalPriceItems = remapRentDetailItem(users, updateRentDetailRequestBodyDTO.getItems(), now);

        RentDetail updatedRentDetail = mapToRentDetail(customer, address, now);
        updatedRentDetail.setTotalAmount(totalPriceItems.getTotalPriceRent());
        updatedRentDetail.setId(rentId);
        updatedRentDetail.setUpdatedBy(users);
        updatedRentDetail.setCreatedBy(rentDetail.getCreatedBy());
        updatedRentDetail.setCreatedAt(rentDetail.getCreatedAt());


        for(RentDetailItem rentDetailItem: totalPriceItems.getRentDetailItems()) {
            rentDetailItem.setRent(updatedRentDetail);
        }

        rentDetailItemRepository.saveAll(totalPriceItems.getRentDetailItems());

        rentDetailRepository.save(rentDetail);
    }

    @Override
    @Transactional
    public DetailRentDetailResponseDTO finishRentDetail(
            Users users,
            Long rentId
    ) throws DataNotExistsException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        RentDetail rentDetail = getRentById(rentId);
        rentDetail.setState(RentDetailState.RETURN);
        rentDetail.setUpdatedAt(now);
        rentDetail.setUpdatedBy(users);

        List<Product> adjustProducts = new ArrayList<>();
        for(RentDetailItem rentDetailItem: rentDetail.getRentDetailItems()) {
            if (!rentDetailItem.getIsDeleted()) {
                Product product = productService.getProductById(rentDetailItem.getProduct().getId());
                Integer latestAvailableQuantity = product.getAvailableQuantity() + rentDetailItem.getQuantity();
                product.setUpdatedAt(now);
                product.setAvailableQuantity(latestAvailableQuantity);
                adjustProducts.add(product);
            }
        }
        productRepository.saveAll(adjustProducts);
        rentDetailRepository.save(rentDetail);

        return mapToDetailRentResponseDTO(rentDetail);
    }
}
