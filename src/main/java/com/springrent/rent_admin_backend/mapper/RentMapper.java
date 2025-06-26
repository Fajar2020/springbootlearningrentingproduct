package com.springrent.rent_admin_backend.mapper;

import com.springrent.rent_admin_backend.dto.address.AddressResponseDTO;
import com.springrent.rent_admin_backend.dto.customers.CustomerResponseDTO;
import com.springrent.rent_admin_backend.dto.products.ProductResponseDTO;
import com.springrent.rent_admin_backend.dto.rent.*;
import com.springrent.rent_admin_backend.models.*;
import com.springrent.rent_admin_backend.models.enums.RentDetailState;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.springrent.rent_admin_backend.mapper.AddressMapper.mapToAddressResponseDTO;
import static com.springrent.rent_admin_backend.mapper.CustomerMapper.mapToCustomerResponseDTO;
import static com.springrent.rent_admin_backend.mapper.ProductMapper.mapToProductResponseDTO;

public class RentMapper {

    public static RentDetailItem mapToRentDetailItem(
            UpdateRentDetailItemRequestBodyDTO updateRentDetailItemRequestBodyDTO,
            Product product,
            Timestamp now
    ) {
        RentDetailItem rentDetailItem = new RentDetailItem();
        rentDetailItem.setProduct(product);
        rentDetailItem.setQuantity(updateRentDetailItemRequestBodyDTO.getQuantity());
        rentDetailItem.setTotalAmount(product.getRentPrice() * updateRentDetailItemRequestBodyDTO.getQuantity());
        rentDetailItem.setIsDeleted(false);
        rentDetailItem.setCreatedAt(now);
        rentDetailItem.setUpdatedAt(now);

        return rentDetailItem;
    }

    public static RentDetail mapToRentDetail(
            Customer customer,
            Address address,
            Timestamp now
    ) {
        RentDetail rentDetail = new RentDetail();
        rentDetail.setState(RentDetailState.RENT);
        rentDetail.setCustomer(customer);
        rentDetail.setAddress(address);
        rentDetail.setIsDeleted(false);
        rentDetail.setUpdatedAt(now);
        return rentDetail;
    }

    public static RentItemResponseDTO mapToRentItemResponseDTO(
            RentDetailItem rentDetailItem
    ) {
        ProductResponseDTO productResponseDTO = mapToProductResponseDTO(rentDetailItem.getProduct());
        return new RentItemResponseDTO(
                rentDetailItem.getId(),
                rentDetailItem.getQuantity(),
                rentDetailItem.getTotalAmount(),
                productResponseDTO
        );
    }

    public static DetailRentDetailResponseDTO mapToDetailRentResponseDTO(
            RentDetail rentDetail
    ) {
        List<RentItemResponseDTO> items = new ArrayList<>();
        for(RentDetailItem rentDetailItem: rentDetail.getRentDetailItems()) {
            if (!rentDetailItem.getIsDeleted()) {
                items.add(mapToRentItemResponseDTO(rentDetailItem));
            }
        }

        CustomerResponseDTO customerResponseDTO = mapToCustomerResponseDTO(rentDetail.getCustomer());
        AddressResponseDTO addressResponseDTO = mapToAddressResponseDTO(rentDetail.getAddress());
        RentDetailResponseDTO rentResponseDTO = new RentDetailResponseDTO(
                rentDetail.getId(),
                rentDetail.getState().toString(),
                rentDetail.getTotalAmount(),
                rentDetail.getCreatedAt(),
                rentDetail.getUpdatedAt(),
                customerResponseDTO,
                addressResponseDTO,
                items
        );
        DetailRentDetailResponseDTO detailRentResponseDTO = new DetailRentDetailResponseDTO();
        detailRentResponseDTO.setMessage("success");
        detailRentResponseDTO.setData(rentResponseDTO);

        return detailRentResponseDTO;
    }

    public static ListRentResponseDTO mapToListRentResponseDTO(
            List<RentDetailBasic> rentDetails
    ) {
        ListRentResponseDTO listRentResponseDTO = new ListRentResponseDTO();
        listRentResponseDTO.setMessage("success");

        List<RentResponseDTO> listRentResponse = new ArrayList<>();

        for(RentDetailBasic rentDetail: rentDetails) {
            RentResponseDTO rentResponseDTO = new RentResponseDTO();
            rentResponseDTO.setId(rentDetail.getId());
            rentResponseDTO.setState(rentDetail.getState().toString());
            rentResponseDTO.setTotalAmount(rentDetail.getTotalAmount());
            rentResponseDTO.setCreatedAt(rentDetail.getCreatedAt());
            rentResponseDTO.setUpdatedAt(rentDetail.getUpdatedAt());

            listRentResponse.add(rentResponseDTO);
        }

        listRentResponseDTO.setData(listRentResponse);
        return listRentResponseDTO;
    }
}
