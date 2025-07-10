package com.springrent.rent_admin_backend.dto.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAddressByCustomerRequestBodyDTO {

    @NotNull
    @NotBlank
    private String addressLine1;

    @NotNull
    @NotBlank
    @Size(min=2, max=255)
    private String title;

    private String addressLine2;

    @NotNull
    @NotBlank
    private String city;

    @NotNull
    @NotBlank
    private String country;


}
