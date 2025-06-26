package com.springrent.rent_admin_backend.mapper;

import com.springrent.rent_admin_backend.dto.address.AddressResponseDTO;
import com.springrent.rent_admin_backend.dto.address.ListAddressResponseDTO;
import com.springrent.rent_admin_backend.dto.address.UpdateAddressByCustomerRequestBodyDTO;
import com.springrent.rent_admin_backend.models.Address;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AddressMapper {

    public static Address mapToAddress(
        UpdateAddressByCustomerRequestBodyDTO updateAddressByCustomerRequestBodyDTO
    ) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Address address = new Address();

        address.setAddressLine1(updateAddressByCustomerRequestBodyDTO.getAddressLine1());
        address.setAddressLine2(updateAddressByCustomerRequestBodyDTO.getAddressLine2());
        address.setCity(updateAddressByCustomerRequestBodyDTO.getCity());
        address.setCountry(updateAddressByCustomerRequestBodyDTO.getCountry());
        address.setIsDeleted(false);
        address.setUpdatedAt(now);
        address.setCreatedAt(now);
        return address;
    }

    public static AddressResponseDTO mapToAddressResponseDTO(Address address) {
        return new AddressResponseDTO(
                address.getId(),
                address.getAddressLine1(),
                address.getAddressLine2(),
                address.getCity(),
                address.getCountry(),
                address.getIsDeleted()
        );
    }

    public static ListAddressResponseDTO mapToListAddressResponseDTO(List<Address> addresses) {
        List<AddressResponseDTO> addressResponseDTOS = new ArrayList<>();
        for(Address address: addresses) {
            addressResponseDTOS.add(mapToAddressResponseDTO(address));
        }

        ListAddressResponseDTO listAddressResponseDTO = new ListAddressResponseDTO();
        listAddressResponseDTO.setMessage("success");
        listAddressResponseDTO.setData(addressResponseDTOS);
        return  listAddressResponseDTO;
    }
}
