package com.springrent.rent_admin_backend.dto.products;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductBodyRequestDTO {

    @NotNull
    @NotBlank
    @Size(min=2, max=255)
    private String name;

    @Size(max=512)
    private String description;

    @NotNull
    @PositiveOrZero
    private Integer quantity;

    @NotNull
    @Positive
    private Double rentPrice;
}
