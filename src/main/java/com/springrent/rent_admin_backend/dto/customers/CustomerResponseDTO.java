package com.springrent.rent_admin_backend.dto.customers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDTO{
    private Long id;
    private String displayName;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
