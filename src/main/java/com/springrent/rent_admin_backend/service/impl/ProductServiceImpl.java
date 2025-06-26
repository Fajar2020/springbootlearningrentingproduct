package com.springrent.rent_admin_backend.service.impl;

import com.springrent.rent_admin_backend.dto.products.DetailProductResponseDTO;
import com.springrent.rent_admin_backend.dto.products.ListProductResponseDTO;
import com.springrent.rent_admin_backend.dto.products.UpdateProductBodyRequestDTO;
import com.springrent.rent_admin_backend.models.Product;
import com.springrent.rent_admin_backend.models.Users;
import com.springrent.rent_admin_backend.repository.ProductRepository;
import com.springrent.rent_admin_backend.exception.DataAlreadyExistsException;
import com.springrent.rent_admin_backend.exception.DataNotExistsException;
import com.springrent.rent_admin_backend.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static com.springrent.rent_admin_backend.mapper.ProductMapper.*;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Override
    public Product getProductById(Long productId) throws DataNotExistsException{
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new DataNotExistsException("Product");
        }

        return optionalProduct.get();
    }

    @Override
    public ListProductResponseDTO getProducts(String name) {
        List<Product> products;
        if (name == null || name.isEmpty()) {
            products = productRepository.findByIsDeletedFalse();
        } else {
            products = productRepository.findByNameAndIsDeletedFalse(name);
        }
        return mapToListProductResponseDTO(products);
    }

    @Override
    public DetailProductResponseDTO addProduct(
            Users users,
            UpdateProductBodyRequestDTO updateProductBodyRequestDTO
    ) throws DataAlreadyExistsException{
        Optional<Product> optionalProduct = productRepository.findByNameIgnoreCaseAndIsDeletedFalse(updateProductBodyRequestDTO.getName());
        if (optionalProduct.isPresent()) {
            throw new DataAlreadyExistsException("Product");
        }

        Product product = mapToProduct(updateProductBodyRequestDTO);
        product.setCreatedBy(users);
        product.setUpdatedBy(users);
        product = productRepository.save(product);
        return mapToDetailProductResponseDTO(product);
    }

    @Override
    public DetailProductResponseDTO updateProduct(
            Users users,
            Long productId,
            UpdateProductBodyRequestDTO updateProductBodyRequestDTO
    ) throws DataNotExistsException, DataAlreadyExistsException{
        Product product = getProductById(productId);
        if (!product.getName().equalsIgnoreCase(updateProductBodyRequestDTO.getName())) {
            Optional<Product> optionalProduct = productRepository.findByNameIgnoreCaseAndIsDeletedFalse(updateProductBodyRequestDTO.getName());
            if (optionalProduct.isPresent()) {
                throw new DataAlreadyExistsException("Customer");
            }
        }

        Product updateProduct = mapToProduct(updateProductBodyRequestDTO);

        Integer additionalProductQuantity = updateProduct.getQuantity() - product.getQuantity();
        Integer currentAvailableQuantity = product.getAvailableQuantity() + additionalProductQuantity;

        updateProduct.setId(productId);
        updateProduct.setCreatedAt(product.getCreatedAt());
        updateProduct.setAvailableQuantity(currentAvailableQuantity);
        updateProduct.setUpdatedBy(users);

        updateProduct = productRepository.save(updateProduct);

        return mapToDetailProductResponseDTO(updateProduct);
    }

    @Override
    public void deleteProduct(
            Users users,
            Long productId
    ) throws DataNotExistsException{
        Product product = getProductById(productId);
        Timestamp now = new Timestamp(System.currentTimeMillis());

        product.setUpdatedAt(now);
        product.setIsDeleted(true);
        product.setUpdatedBy(users);
        productRepository.save(product);
    }
}
