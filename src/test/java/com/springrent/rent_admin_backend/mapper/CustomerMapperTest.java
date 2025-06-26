package com.springrent.rent_admin_backend.mapper;

import com.springrent.rent_admin_backend.dto.customers.CustomerAddressResponseDTO;
import com.springrent.rent_admin_backend.dto.customers.DetailCustomerAddressResponseDTO;
import com.springrent.rent_admin_backend.dto.customers.UpdateCustomerRequestBodyDTO;
import com.springrent.rent_admin_backend.models.Address;
import com.springrent.rent_admin_backend.models.Customer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class CustomerMapperTest {

    @Autowired
    private CustomerMapper customerMapper;

    Timestamp now = new Timestamp(System.currentTimeMillis());
    @Test
    public void testMapToCustomer() {
        UpdateCustomerRequestBodyDTO input = new UpdateCustomerRequestBodyDTO("display name", "first name", "last name", "customer1@test.com", "085783211231");
        Customer customerFromMap = customerMapper.mapToCustomer(input);

        assertEquals("display name", customerFromMap.getDisplayName());
        assertEquals("first name", customerFromMap.getFirstName());
        assertEquals("last name", customerFromMap.getLastName());
        assertEquals("customer1@test.com", customerFromMap.getEmail());
        assertEquals("085783211231", customerFromMap.getPhone());
        assertFalse(customerFromMap.getIsDeleted());

    }

    @Test
    public void testMapToDetailCustomerAddressResponseDTO(){
        Customer customer = new Customer();

        List<Address> addresses = new ArrayList<>();
        Address addressIsDeletedFalse = new Address();
        addressIsDeletedFalse.setIsDeleted(false);
        Address addressIsDeletedTrue = new Address();
        addressIsDeletedTrue.setIsDeleted(true);
        addresses.add(addressIsDeletedTrue);
        addresses.add(addressIsDeletedFalse);

        customer.setAddresses(addresses);
        customer.setId((long) 1);
        customer.setDisplayName("orgrek");
        customer.setFirstName("orchid");
        customer.setLastName("anggrek");
        customer.setEmail("orgrek@test.com");
        customer.setPhone( "085783211231");
        customer.setIsDeleted(false);
        customer.setCreatedAt(now);
        customer.setUpdatedAt(now);

        DetailCustomerAddressResponseDTO responseDTO = customerMapper.mapToDetailCustomerAddressResponseDTO(customer);

        assertEquals("success", responseDTO.getMessage());

        CustomerAddressResponseDTO customerAddressResponseDTO = responseDTO.getData();
        assertEquals(1, customerAddressResponseDTO.getAddresses().size());
        assertEquals((long) 1, customerAddressResponseDTO.getId());
        assertEquals("orgrek", customerAddressResponseDTO.getDisplayName());
        assertEquals("orchid", customerAddressResponseDTO.getFirstName());
        assertEquals("anggrek", customerAddressResponseDTO.getLastName());
        assertEquals("orgrek@test.com", customerAddressResponseDTO.getEmail());
        assertEquals( "085783211231", customerAddressResponseDTO.getPhone());
    }
}
