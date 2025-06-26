package com.springrent.rent_admin_backend.mapper;

import com.springrent.rent_admin_backend.dto.address.AddressResponseDTO;
import com.springrent.rent_admin_backend.dto.customers.*;
import com.springrent.rent_admin_backend.models.Address;
import com.springrent.rent_admin_backend.models.Customer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.springrent.rent_admin_backend.mapper.AddressMapper.mapToAddressResponseDTO;

public class CustomerMapper {

    public static Customer mapToCustomer(
            UpdateCustomerRequestBodyDTO updateCustomerRequestBodyDTO
    ) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Customer customer = new Customer();
        customer.setDisplayName(updateCustomerRequestBodyDTO.getDisplayName());
        customer.setFirstName(updateCustomerRequestBodyDTO.getFirstName());
        customer.setLastName(updateCustomerRequestBodyDTO.getLastName());
        customer.setEmail(updateCustomerRequestBodyDTO.getEmail());
        customer.setPhone(updateCustomerRequestBodyDTO.getPhone());
        customer.setIsDeleted(false);
        customer.setUpdatedAt(now);

        return  customer;
    }

    public static DetailCustomerAddressResponseDTO mapToDetailCustomerAddressResponseDTO(Customer customer) {
        ArrayList<AddressResponseDTO> addresses = new ArrayList<AddressResponseDTO>();
        for(Address address: customer.getAddresses()) {
            if (!address.getIsDeleted()) {
                addresses.add(mapToAddressResponseDTO(address));
            }
        }

        CustomerAddressResponseDTO customerAddressResponseDTO = new CustomerAddressResponseDTO();
        customerAddressResponseDTO.setId(customer.getId());
        customerAddressResponseDTO.setDisplayName(customer.getDisplayName());
        customerAddressResponseDTO.setFirstName(customer.getFirstName());
        customerAddressResponseDTO.setLastName(customer.getLastName());
        customerAddressResponseDTO.setEmail(customer.getEmail());
        customerAddressResponseDTO.setPhone(customer.getPhone());
        customerAddressResponseDTO.setAddresses(addresses);

        DetailCustomerAddressResponseDTO detailCustomerResponseDTO = new DetailCustomerAddressResponseDTO();
        detailCustomerResponseDTO.setMessage("success");
        detailCustomerResponseDTO.setData(customerAddressResponseDTO);

        return detailCustomerResponseDTO;
    }

    public static CustomerResponseDTO mapToCustomerResponseDTO(Customer customer) {
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getDisplayName(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone()
        );
    }

    public static ListCustomerResponseDTO mapToCustomerResponseDTOForList(List<Customer> customers) {
        List<CustomerResponseDTO> customerResponseDTOs = new ArrayList<CustomerResponseDTO>();
        for(Customer customer: customers) {
            customerResponseDTOs.add(mapToCustomerResponseDTO(customer));
        }

        ListCustomerResponseDTO listCustomerResponseDTO = new ListCustomerResponseDTO();
        listCustomerResponseDTO.setData(customerResponseDTOs);
        listCustomerResponseDTO.setMessage("success");

        return listCustomerResponseDTO;
    }
}
