package com.springrent.rent_admin_backend.dto.rent;

import com.springrent.rent_admin_backend.dto.address.AddressResponseDTO;
import com.springrent.rent_admin_backend.dto.customers.CustomerResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RentDetailResponseDTO {
    private Long id;
    private String state;
    private Double totalAmount;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private CustomerResponseDTO customer;
    private AddressResponseDTO address;
    private List<RentItemResponseDTO> items;
}


