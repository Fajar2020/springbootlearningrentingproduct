package com.springrent.rent_admin_backend.api.controllers;

import com.springrent.rent_admin_backend.dto.products.DetailProductResponseDTO;
import com.springrent.rent_admin_backend.dto.products.ListProductResponseDTO;
import com.springrent.rent_admin_backend.dto.products.UpdateProductBodyRequestDTO;
import com.springrent.rent_admin_backend.models.Users;
import com.springrent.rent_admin_backend.exception.DataAlreadyExistsException;
import com.springrent.rent_admin_backend.exception.DataNotExistsException;
import com.springrent.rent_admin_backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @GetMapping()
    public ResponseEntity<ListProductResponseDTO> getProducts(
            @RequestParam(value = "displayName", required = false) String name
    ) {
        return ResponseEntity.ok(productService.getProducts(name));
    }

    @PostMapping()
    public ResponseEntity<DetailProductResponseDTO> addProduct(
            @AuthenticationPrincipal Users users,
            @Valid @RequestBody UpdateProductBodyRequestDTO updateProductBodyRequestDTO
    ) {
        DetailProductResponseDTO detailProductResponseDTO = new DetailProductResponseDTO();
        try {
            detailProductResponseDTO = productService.addProduct(users, updateProductBodyRequestDTO);
            return ResponseEntity.ok(detailProductResponseDTO);
        } catch (DataAlreadyExistsException e) {
            detailProductResponseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detailProductResponseDTO);
        }
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<DetailProductResponseDTO> updateProduct(
            @AuthenticationPrincipal Users users,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateProductBodyRequestDTO updateProductBodyRequestDTO
    ) {
        DetailProductResponseDTO detailProductResponseDTO = new DetailProductResponseDTO();
        try {
            detailProductResponseDTO = productService.updateProduct(users, productId, updateProductBodyRequestDTO);
            return ResponseEntity.ok(detailProductResponseDTO);
        } catch (DataNotExistsException e) {
            detailProductResponseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detailProductResponseDTO);
        } catch (DataAlreadyExistsException e) {
            detailProductResponseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detailProductResponseDTO);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<DetailProductResponseDTO> deleteProduct(
            @AuthenticationPrincipal Users users,
            @PathVariable Long productId
    ) {
        DetailProductResponseDTO detailProductResponseDTO = new DetailProductResponseDTO();
        try {
            productService.deleteProduct(users, productId);
            return ResponseEntity.noContent().build();
        } catch (DataNotExistsException e) {
            detailProductResponseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detailProductResponseDTO);
        }
    }
}
