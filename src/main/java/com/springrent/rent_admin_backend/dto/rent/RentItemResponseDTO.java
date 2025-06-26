package com.springrent.rent_admin_backend.dto.rent;

import com.springrent.rent_admin_backend.dto.products.ProductResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RentItemResponseDTO {
    private Long id;
    private Integer quantity;
    private Double totalAmount;
    private ProductResponseDTO product;
}
