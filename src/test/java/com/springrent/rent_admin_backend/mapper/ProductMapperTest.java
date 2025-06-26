package com.springrent.rent_admin_backend.mapper;

import com.springrent.rent_admin_backend.dto.products.ProductResponseDTO;
import com.springrent.rent_admin_backend.dto.products.UpdateProductBodyRequestDTO;
import com.springrent.rent_admin_backend.models.Product;
import com.springrent.rent_admin_backend.models.Users;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void testMapToProduct() {
        UpdateProductBodyRequestDTO input = new UpdateProductBodyRequestDTO("product", "this product is good", 100, 50000.0);

        Product product = productMapper.mapToProduct(input);
        assertEquals("product", product.getName());
        assertEquals("this product is good", product.getDescription());
        assertEquals(100, product.getQuantity());
        assertEquals(100, product.getAvailableQuantity());
        assertEquals(50000.0, product.getRentPrice());
        assertFalse(product.getIsDeleted());
    }

    @Test
    public void testMapToProductResponseDTO() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Users users = new Users();
        Product product = new Product((long) 1, "product name", "product description", 100, 90, 40000.0, false, users, now, users, now);

        ProductResponseDTO responseDTO = productMapper.mapToProductResponseDTO(product);
        assertEquals(1, responseDTO.getId());
        assertEquals("product name", responseDTO.getName());
        assertEquals("product description", responseDTO.getDescription());
        assertEquals(100, responseDTO.getQuantity());
        assertEquals(90, responseDTO.getAvailableQuantity());
        assertEquals(40000.0, responseDTO.getRentPrice());
        assertFalse(product.getIsDeleted());
    }
}
