package com.springrent.rent_admin_backend.mapper;

import com.springrent.rent_admin_backend.dto.address.AddressResponseDTO;
import com.springrent.rent_admin_backend.dto.address.ListAddressResponseDTO;
import com.springrent.rent_admin_backend.dto.address.UpdateAddressByCustomerRequestBodyDTO;
import com.springrent.rent_admin_backend.models.Address;
import com.springrent.rent_admin_backend.models.Customer;
import com.springrent.rent_admin_backend.models.Users;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AddressMapperTest {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void testMapToAddress() {
        UpdateAddressByCustomerRequestBodyDTO input = new UpdateAddressByCustomerRequestBodyDTO(
                "address line 1",
                "address line 2",
                "Madiun",
                "Indonesia"
        );

        Address address = addressMapper.mapToAddress(input);
        assertEquals("address line 1", address.getAddressLine1());
        assertEquals("address line 2", address.getAddressLine2());
        assertEquals("Madiun", address.getCity());
        assertEquals("Indonesia", address.getCountry());
        assertFalse(address.getIsDeleted());

        input.setAddressLine2(null);
        address = addressMapper.mapToAddress(input);
        assertNull(address.getAddressLine2());
    }

    @Test
    public void testMapToAddressResponseDTO() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Customer customer = new Customer();
        Users users = new Users();
        Address address = new Address(
                (long)1,
                "addressLine1",
                "addressLine2",
                "Madiun",
                "Indonesia",
                false,
                customer, users, users, now, now);

        AddressResponseDTO addressResponseDTO = addressMapper.mapToAddressResponseDTO(address);
        assertEquals(1, addressResponseDTO.getId());
        assertEquals("addressLine1", addressResponseDTO.getAddressLine1());
        assertEquals("addressLine2", addressResponseDTO.getAddressLine2());
        assertEquals("Madiun", addressResponseDTO.getCity());
        assertEquals("Indonesia", addressResponseDTO.getCountry());
        assertFalse(addressResponseDTO.getIsDeleted());
    }

    @Test
    public void testMapToListAddressResponseDTO() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address());
        addresses.add(new Address());

        ListAddressResponseDTO listAddressResponseDTO = addressMapper.mapToListAddressResponseDTO(addresses);
        assertEquals("success", listAddressResponseDTO.getMessage());
        assertEquals(2, listAddressResponseDTO.getData().size());
    }
}
