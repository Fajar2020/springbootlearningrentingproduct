package com.springrent.rent_admin_backend.dto.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private Integer availableQuantity;
    private Double rentPrice;
}
