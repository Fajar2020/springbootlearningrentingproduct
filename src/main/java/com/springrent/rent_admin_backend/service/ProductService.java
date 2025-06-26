package com.springrent.rent_admin_backend.service;

import com.springrent.rent_admin_backend.dto.products.DetailProductResponseDTO;
import com.springrent.rent_admin_backend.dto.products.ListProductResponseDTO;
import com.springrent.rent_admin_backend.dto.products.UpdateProductBodyRequestDTO;
import com.springrent.rent_admin_backend.exception.DataAlreadyExistsException;
import com.springrent.rent_admin_backend.exception.DataNotExistsException;
import com.springrent.rent_admin_backend.models.Product;
import com.springrent.rent_admin_backend.models.Users;

public interface ProductService {
    Product getProductById(Long productId) throws DataNotExistsException;
    ListProductResponseDTO getProducts(String name);
    DetailProductResponseDTO addProduct(
            Users users,
            UpdateProductBodyRequestDTO updateProductBodyRequestDTO
    ) throws DataAlreadyExistsException;
    DetailProductResponseDTO updateProduct(
            Users users,
            Long productId,
            UpdateProductBodyRequestDTO updateProductBodyRequestDTO
    ) throws DataNotExistsException, DataAlreadyExistsException;
    void deleteProduct(
            Users users,
            Long productId
    ) throws DataNotExistsException;
}
