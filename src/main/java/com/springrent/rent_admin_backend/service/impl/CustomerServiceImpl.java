package com.springrent.rent_admin_backend.service.impl;

import com.springrent.rent_admin_backend.dto.address.AddressResponseDTO;
import com.springrent.rent_admin_backend.dto.address.ListAddressResponseDTO;
import com.springrent.rent_admin_backend.dto.address.UpdateAddressByCustomerRequestBodyDTO;
import com.springrent.rent_admin_backend.dto.customers.CustomerResponseDTO;
import com.springrent.rent_admin_backend.dto.customers.DetailCustomerAddressResponseDTO;
import com.springrent.rent_admin_backend.dto.customers.ListCustomerResponseDTO;
import com.springrent.rent_admin_backend.dto.customers.UpdateCustomerRequestBodyDTO;
import com.springrent.rent_admin_backend.models.Address;
import com.springrent.rent_admin_backend.models.Customer;
import com.springrent.rent_admin_backend.models.Users;
import com.springrent.rent_admin_backend.repository.AddressRepository;
import com.springrent.rent_admin_backend.repository.CustomerRepository;
import com.springrent.rent_admin_backend.exception.DataAlreadyExistsException;
import com.springrent.rent_admin_backend.exception.DataNotExistsException;
import com.springrent.rent_admin_backend.exception.InvalidDataException;
import com.springrent.rent_admin_backend.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static com.springrent.rent_admin_backend.mapper.AddressMapper.*;
import static com.springrent.rent_admin_backend.mapper.CustomerMapper.*;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private AddressRepository addressRepository;

    @Override
    public Customer getCustomerById(Long customerId) throws DataNotExistsException {
        Optional<Customer> optionalCustomer = customerRepository.findByIdAndIsDeletedFalse(customerId);

        if (optionalCustomer.isEmpty()) {
            throw new DataNotExistsException("Customer");
        }

        return optionalCustomer.get();
    }

    @Override
    public Address getAddressById(Long addressId) throws DataNotExistsException {
        Optional<Address> optionalAddress = addressRepository.findByIdAndIsDeletedFalse(addressId);
        if (optionalAddress.isEmpty()) {
            throw new DataNotExistsException("Address");
        }
        return optionalAddress.get();
    }

    @Override
    public DetailCustomerAddressResponseDTO getCustomerAddressDetailById(
            Long customerId
    ) throws DataNotExistsException{
        Customer customer = getCustomerById(customerId);
        return mapToDetailCustomerAddressResponseDTO(customer);
    }

    @Override
    public ListCustomerResponseDTO getCustomers(
            String displayName
    ) {
        List<Customer> customers;
        if (displayName == null || displayName.isEmpty()) {
            customers  = customerRepository.findByIsDeletedFalse();
        } else {
            customers = customerRepository.findByDisplayNameLikeIgnoreCaseAndIsDeletedFalse(displayName);
        }
        return mapToCustomerResponseDTOForList(customers);
    }

    @Override
    public CustomerResponseDTO addCustomer(
            Users users,
            UpdateCustomerRequestBodyDTO updateCustomerRequestBodyDTO
    ) throws DataAlreadyExistsException {
        Optional<Customer> optionalCustomer = customerRepository.findByEmailIgnoreCaseAndIsDeletedFalse(updateCustomerRequestBodyDTO.getEmail());

        if (optionalCustomer.isPresent()) {
            throw new DataAlreadyExistsException("Customer email");
        }

        Optional<Customer> optionalCustomerDisplayName = customerRepository.findByDisplayNameIgnoreCaseAndIsDeletedFalse(updateCustomerRequestBodyDTO.getDisplayName());

        if (optionalCustomerDisplayName.isPresent()) {
            throw new DataAlreadyExistsException("Customer display name");
        }

        Timestamp now = new Timestamp(System.currentTimeMillis());
        Customer customer = mapToCustomer(updateCustomerRequestBodyDTO);
        customer.setCreatedAt(now);
        customer.setCreatedBy(users);
        customer.setUpdatedBy(users);
        Customer savedCustomer = customerRepository.save(customer);

        return mapToCustomerResponseDTO(savedCustomer);
    }

    @Override
    public CustomerResponseDTO updateCustomer(
            Users users,
            Long customerId,
            UpdateCustomerRequestBodyDTO updateCustomerRequestBodyDTO
    ) throws DataNotExistsException, DataAlreadyExistsException {
        Customer customer = getCustomerById(customerId);
        if (!customer.getEmail().equalsIgnoreCase(updateCustomerRequestBodyDTO.getEmail())) {
            Optional<Customer> optionalCustomer = customerRepository.findByEmailIgnoreCaseAndIsDeletedFalse(updateCustomerRequestBodyDTO.getEmail());
            if (optionalCustomer.isPresent()) {
                throw new DataAlreadyExistsException("Customer email");
            }
        }

        if (!customer.getDisplayName().equalsIgnoreCase(updateCustomerRequestBodyDTO.getDisplayName())) {
            Optional<Customer> optionalCustomer = customerRepository.findByDisplayNameIgnoreCaseAndIsDeletedFalse(updateCustomerRequestBodyDTO.getDisplayName());
            if (optionalCustomer.isPresent()) {
                throw new DataAlreadyExistsException("Customer display nama");
            }
        }

        Customer newCustomerMapping = mapToCustomer(updateCustomerRequestBodyDTO);
        newCustomerMapping.setId(customer.getId());
        newCustomerMapping.setCreatedAt(customer.getCreatedAt());
        newCustomerMapping.setCreatedBy(customer.getCreatedBy());
        newCustomerMapping.setAddresses(customer.getAddresses());
        newCustomerMapping.setUpdatedBy(users);

        customer = customerRepository.save(newCustomerMapping);

        return mapToCustomerResponseDTO(customer);
    }

    @Override
    public void deleteCustomer(
            Users users,
            Long customerId
    ) throws DataNotExistsException {
        Customer customer = getCustomerById(customerId);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        customer.setIsDeleted(true);
        customer.setUpdatedAt(now);
        customer.setUpdatedBy(users);

        customerRepository.save(customer);
    }

    @Override
    public ListAddressResponseDTO getAddress(
            Long customerId
    ) throws DataNotExistsException {
        Customer customer = getCustomerById(customerId);

        return mapToListAddressResponseDTO(customer.getAddresses());
    }

    @Override
    public AddressResponseDTO addAddress(
            Users users,
            Long customerId,
            UpdateAddressByCustomerRequestBodyDTO updateAddressByCustomerRequestBodyDTO
    ) throws DataNotExistsException {
        Customer customer = getCustomerById(customerId);

        Address address = mapToAddress(updateAddressByCustomerRequestBodyDTO);
        address.setCustomer(customer);
        address.setCreatedBy(users);
        address.setUpdatedBy(users);

        address = addressRepository.save(address);

        return mapToAddressResponseDTO(address);
    }

    @Override
    public AddressResponseDTO updateAddress(
            Users users,
            Long customerId,
            Long addressId,
            UpdateAddressByCustomerRequestBodyDTO updateAddressByCustomerRequestBodyDTO
    ) throws DataNotExistsException, InvalidDataException {
        Customer customer = getCustomerById(customerId);
        Address address = getAddressById(addressId);

        if (!address.getCustomer().getId().equals(customerId)) {
            throw new InvalidDataException();
        }

        Address updatedAddress = mapToAddress(updateAddressByCustomerRequestBodyDTO);
        updatedAddress.setId(addressId);
        updatedAddress.setCustomer(customer);
        updatedAddress.setCreatedAt(address.getCreatedAt());
        updatedAddress.setCreatedBy(address.getCreatedBy());
        updatedAddress.setUpdatedBy(users);

        updatedAddress = addressRepository.save(updatedAddress);

        return mapToAddressResponseDTO(updatedAddress);
    }

    @Override
    public void deleteAddress(
            Users users,
            Long customerId,
            Long addressId
    ) throws DataNotExistsException, InvalidDataException {
        Address address = getAddressById(addressId);
        if (!address.getCustomer().getId().equals(customerId)) {
            throw new InvalidDataException();
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());

        address.setUpdatedAt(now);
        address.setIsDeleted(true);
        address.setUpdatedBy(users);
        addressRepository.save(address);
    }
}
