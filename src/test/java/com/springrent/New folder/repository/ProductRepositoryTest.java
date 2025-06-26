package com.springrent.rent_admin_backend.repository;

import com.springrent.rent_admin_backend.models.Product;
import com.springrent.rent_admin_backend.models.Users;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void UsersRepository_FindByUsernameIgnoreCaseAndIsDeletedFalse_ReturnUsers() {
        Product product = Product.builder()
                .name("pro")
                .description("desc")
                .quantity(100)
                .availableQuantity(100)
                .rentPrice(100.0)
                .isDeleted(false).build();

        productRepository.save(product);
    }
}
