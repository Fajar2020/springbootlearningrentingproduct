package com.springrent.rent_admin_backend.dto.rent;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRentDetailRequestBodyDTO {

    @NotNull
    @Positive
    private Long customerId;

    @NotNull
    @Positive
    private Long addressId;

    @NotEmpty
    @Size(min = 1)
    private List<UpdateRentDetailItemRequestBodyDTO> items;

}
