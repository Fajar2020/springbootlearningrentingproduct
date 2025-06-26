package com.springrent.rent_admin_backend.mapper;

import com.springrent.rent_admin_backend.dto.products.DetailProductResponseDTO;
import com.springrent.rent_admin_backend.dto.products.ListProductResponseDTO;
import com.springrent.rent_admin_backend.dto.products.ProductResponseDTO;
import com.springrent.rent_admin_backend.dto.products.UpdateProductBodyRequestDTO;
import com.springrent.rent_admin_backend.models.Product;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProductMapper {

    public static Product mapToProduct(UpdateProductBodyRequestDTO updateProductBodyRequestDTO) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Product product = new Product();
        product.setName(updateProductBodyRequestDTO.getName());
        product.setDescription(updateProductBodyRequestDTO.getDescription());
        product.setQuantity(updateProductBodyRequestDTO.getQuantity());
        product.setAvailableQuantity(updateProductBodyRequestDTO.getQuantity());
        product.setRentPrice(updateProductBodyRequestDTO.getRentPrice());
        product.setIsDeleted(false);
        product.setCreatedAt(now);
        product.setUpdatedAt(now);
        return product;
    }

    static ProductResponseDTO mapToProductResponseDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getQuantity(),
                product.getAvailableQuantity(),
                product.getRentPrice()
        );
    }

    public static ListProductResponseDTO mapToListProductResponseDTO(List<Product> products) {
        List<ProductResponseDTO> productResponses = new ArrayList<ProductResponseDTO>();
        for(Product product: products) {
            productResponses.add(mapToProductResponseDTO(product));
        }
        ListProductResponseDTO listProductResponseDTO = new ListProductResponseDTO();
        listProductResponseDTO.setData(productResponses);
        listProductResponseDTO.setMessage("success");
        return listProductResponseDTO;
    }

    public static DetailProductResponseDTO mapToDetailProductResponseDTO(Product product) {
        DetailProductResponseDTO detailProductResponseDTO = new DetailProductResponseDTO();
        detailProductResponseDTO.setData(mapToProductResponseDTO(product));
        detailProductResponseDTO.setMessage("success");
        return  detailProductResponseDTO;
    }
}
