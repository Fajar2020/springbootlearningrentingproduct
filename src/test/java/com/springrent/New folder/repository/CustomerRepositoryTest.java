package com.springrent.rent_admin_backend.repository;

import com.springrent.rent_admin_backend.models.Customer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void CustomerRepostiory_FindByEmailIgnoreCaseAndIsDeletedFalse_ReturnCustomer() {
        Customer customer = Customer.builder().build();

    }
}
