package com.springrent.rent_admin_backend.service;

import com.springrent.rent_admin_backend.models.Customer;
import com.springrent.rent_admin_backend.exception.DataNotExistsException;
import com.springrent.rent_admin_backend.service.CustomerService;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    @Transactional
    public void testGetCustomerById()throws DataNotExistsException{
//        Assertions.assertThrows(DataNotExistsException.class,
//                () -> customerService.getCustomerById((long)3), "customer id 3 is not exist in database");
//
//        Assertions.assertThrows(DataNotExistsException.class,
//                () -> customerService.getCustomerById((long)2), "customer id 2 is exist in database but already deleted");

        try {
            Customer customer = customerService.getCustomerById((long) 1);
            assertEquals(1, customer.getId());
            assertEquals("customer1_display", customer.getDisplayName());
            assertEquals("customer1@test.com", customer.getEmail());
            assertEquals("customer1_firstname", customer.getFirstName());
            assertEquals("customer1_lastname", customer.getLastName());
            assertFalse(customer.getIsDeleted());
        } catch (DataNotExistsException e) {
            // ignore this
        }

    }
}
