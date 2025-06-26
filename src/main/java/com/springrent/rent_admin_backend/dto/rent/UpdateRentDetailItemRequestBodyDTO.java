package com.springrent.rent_admin_backend.dto.rent;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentDetailItemRequestBodyDTO {

    @NotNull
    @Positive
    private Long productId;

    @Positive
    private Integer quantity;
}
