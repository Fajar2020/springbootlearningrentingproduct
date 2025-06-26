package com.springrent.rent_admin_backend.dto.address;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDTO {
    private Long id;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String country;
    private Boolean isDeleted;
}
