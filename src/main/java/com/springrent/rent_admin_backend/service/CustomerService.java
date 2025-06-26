package com.springrent.rent_admin_backend.service;

import com.springrent.rent_admin_backend.dto.address.AddressResponseDTO;
import com.springrent.rent_admin_backend.dto.address.ListAddressResponseDTO;
import com.springrent.rent_admin_backend.dto.address.UpdateAddressByCustomerRequestBodyDTO;
import com.springrent.rent_admin_backend.dto.customers.CustomerResponseDTO;
import com.springrent.rent_admin_backend.dto.customers.DetailCustomerAddressResponseDTO;
import com.springrent.rent_admin_backend.dto.customers.ListCustomerResponseDTO;
import com.springrent.rent_admin_backend.dto.customers.UpdateCustomerRequestBodyDTO;
import com.springrent.rent_admin_backend.exception.DataAlreadyExistsException;
import com.springrent.rent_admin_backend.exception.DataNotExistsException;
import com.springrent.rent_admin_backend.exception.InvalidDataException;
import com.springrent.rent_admin_backend.models.Address;
import com.springrent.rent_admin_backend.models.Customer;
import com.springrent.rent_admin_backend.models.Users;

public interface CustomerService {
    Customer getCustomerById(Long customerId) throws DataNotExistsException;
    Address getAddressById(Long addressId) throws DataNotExistsException;
    DetailCustomerAddressResponseDTO getCustomerAddressDetailById(Long customerId) throws DataNotExistsException;
    ListCustomerResponseDTO getCustomers(String displayName);
    CustomerResponseDTO addCustomer(
            Users users,
            UpdateCustomerRequestBodyDTO updateCustomerRequestBodyDTO
    ) throws DataAlreadyExistsException;
    CustomerResponseDTO updateCustomer(
            Users users,
            Long customerId,
            UpdateCustomerRequestBodyDTO updateCustomerRequestBodyDTO
    ) throws DataNotExistsException, DataAlreadyExistsException;
    void deleteCustomer(Users users, Long customerId) throws DataNotExistsException;
    ListAddressResponseDTO getAddress(Long customerId) throws DataNotExistsException;
    AddressResponseDTO addAddress(
            Users users,
            Long customerId,
            UpdateAddressByCustomerRequestBodyDTO updateAddressByCustomerRequestBodyDTO
    ) throws DataNotExistsException;
    AddressResponseDTO updateAddress(
            Users users,
            Long customerId,
            Long addressId,
            UpdateAddressByCustomerRequestBodyDTO updateAddressByCustomerRequestBodyDTO
    ) throws DataNotExistsException, InvalidDataException;
    void deleteAddress(
            Users users,
            Long customerId,
            Long addressId
    ) throws DataNotExistsException, InvalidDataException;
}
