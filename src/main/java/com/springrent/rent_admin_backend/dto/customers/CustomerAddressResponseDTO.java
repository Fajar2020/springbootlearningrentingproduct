package com.springrent.rent_admin_backend.dto.customers;

import com.springrent.rent_admin_backend.dto.address.AddressResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddressResponseDTO extends CustomerResponseDTO{
    private ArrayList<AddressResponseDTO> addresses;
}
