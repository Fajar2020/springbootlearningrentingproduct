package com.springrent.rent_admin_backend.dto.customers;


import jakarta.validation.constraints.*;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerRequestBodyDTO {

    @NotNull
    @Size(min=2, max=255)
    private String displayName;

    @NotNull
    @Size(min=2, max=255)
    private String firstName;

    @Size(max=255)
    private String lastName;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    private String phone;

}
