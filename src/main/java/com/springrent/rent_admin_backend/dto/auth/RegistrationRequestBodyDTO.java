package com.springrent.rent_admin_backend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestBodyDTO {
    @NotNull
    @Size(min=2, max=255)
    private String username;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    @Size(min=6, max=32)
    private String password;

    @NotNull
    @NotBlank
    @Size(min=6, max=6, message = "employeeNumber should be 6 characters length")
    private String employeeNumber;
}
