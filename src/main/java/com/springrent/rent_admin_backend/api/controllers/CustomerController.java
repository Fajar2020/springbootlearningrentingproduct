package com.springrent.rent_admin_backend.api.controllers;

import com.springrent.rent_admin_backend.dto.address.AddressResponseDTO;
import com.springrent.rent_admin_backend.dto.address.DetailAddressResponseDTO;
import com.springrent.rent_admin_backend.dto.address.ListAddressResponseDTO;
import com.springrent.rent_admin_backend.dto.address.UpdateAddressByCustomerRequestBodyDTO;
import com.springrent.rent_admin_backend.dto.customers.*;
import com.springrent.rent_admin_backend.models.Users;
import com.springrent.rent_admin_backend.exception.DataAlreadyExistsException;
import com.springrent.rent_admin_backend.exception.DataNotExistsException;
import com.springrent.rent_admin_backend.exception.InvalidDataException;
import com.springrent.rent_admin_backend.service.CustomerService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;

    @GetMapping()
    public ResponseEntity<ListCustomerResponseDTO> getCustomers(
            @RequestParam(value = "displayName", required = false) String displayName
    ) {
        return ResponseEntity.ok(customerService.getCustomers(displayName));
    }

    @PostMapping()
    public ResponseEntity<DetailCustomerResponseDTO> addCustomer(
            @AuthenticationPrincipal Users users,
            @Valid @RequestBody UpdateCustomerRequestBodyDTO updateCustomerRequestBodyDTO
    ) {
        DetailCustomerResponseDTO detailCustomerResponseDTO = new DetailCustomerResponseDTO();

        try {
            CustomerResponseDTO customerResponseDTO = customerService.addCustomer(
                    users,
                    updateCustomerRequestBodyDTO
            );
            detailCustomerResponseDTO.setMessage("success");
            detailCustomerResponseDTO.setData(customerResponseDTO);
            return ResponseEntity.ok(detailCustomerResponseDTO);
        } catch (DataAlreadyExistsException e) {
            detailCustomerResponseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detailCustomerResponseDTO);
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<DetailCustomerAddressResponseDTO> getCustomers(
            @PathVariable Long customerId
    ) {
        DetailCustomerAddressResponseDTO detailCustomerAddressResponseDTO = new DetailCustomerAddressResponseDTO();
        try {
            detailCustomerAddressResponseDTO = customerService.getCustomerAddressDetailById(customerId);
            return ResponseEntity.ok(detailCustomerAddressResponseDTO);
        } catch (DataNotExistsException e) {
            detailCustomerAddressResponseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detailCustomerAddressResponseDTO);
        }

    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<DetailCustomerResponseDTO> updateCustomer(
            @AuthenticationPrincipal Users users,
            @PathVariable Long customerId,
            @RequestBody UpdateCustomerRequestBodyDTO updateCustomerRequestBodyDTO
    ) {
        DetailCustomerResponseDTO detailCustomerResponseDTO = new DetailCustomerResponseDTO();

        try {
            CustomerResponseDTO customerResponseDTO = customerService.updateCustomer(
                    users,
                    customerId,
                    updateCustomerRequestBodyDTO
            );
            detailCustomerResponseDTO.setMessage("success");
            detailCustomerResponseDTO.setData(customerResponseDTO);
            return ResponseEntity.ok(detailCustomerResponseDTO);
        } catch (DataNotExistsException e) {
            detailCustomerResponseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detailCustomerResponseDTO);
        } catch (DataAlreadyExistsException e) {
            detailCustomerResponseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detailCustomerResponseDTO);
        }
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<DetailCustomerAddressResponseDTO> deleteCustomer(
            @AuthenticationPrincipal Users users,
            @PathVariable Long customerId
    ) {
        DetailCustomerAddressResponseDTO detailCustomerResponseDTO = new DetailCustomerAddressResponseDTO();

        try {
            customerService.deleteCustomer(users, customerId);
            return ResponseEntity.noContent().build();
        } catch (DataNotExistsException e) {
            detailCustomerResponseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detailCustomerResponseDTO);
        }
    }

    @GetMapping("/{customerId}/address")
    public ResponseEntity<ListAddressResponseDTO> getAddress(
            @AuthenticationPrincipal Users users,
            @PathVariable Long customerId
    ) {
        ListAddressResponseDTO listAddressResponseDTO = new ListAddressResponseDTO();

        try {
            listAddressResponseDTO = customerService.getAddress(customerId);
            return ResponseEntity.ok(listAddressResponseDTO);
        } catch (DataNotExistsException e) {
            listAddressResponseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(listAddressResponseDTO);
        }
    }

    @PostMapping("/{customerId}/address")
    public ResponseEntity<DetailAddressResponseDTO> addAddress(
            @AuthenticationPrincipal Users users,
            @PathVariable Long customerId,
            @RequestBody UpdateAddressByCustomerRequestBodyDTO updateAddressByCustomerRequestBodyDTO
            ) {
        DetailAddressResponseDTO detailResponseDTO = new DetailAddressResponseDTO();

        try {
            AddressResponseDTO addressResponseDTO = customerService.addAddress(users, customerId, updateAddressByCustomerRequestBodyDTO);
            detailResponseDTO.setMessage("success");
            detailResponseDTO.setData(addressResponseDTO);
            return ResponseEntity.ok(detailResponseDTO);
        } catch (DataNotExistsException e) {
            detailResponseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detailResponseDTO);
        }
    }

    @PatchMapping("/{customerId}/address/{addressId}")
    public ResponseEntity<DetailAddressResponseDTO> updateAddress(
            @AuthenticationPrincipal Users users,
            @PathVariable Long customerId,
            @PathVariable Long addressId,
            @RequestBody UpdateAddressByCustomerRequestBodyDTO updateAddressByCustomerRequestBodyDTO
    ) {
        DetailAddressResponseDTO detailResponseDTO = new DetailAddressResponseDTO();

        try {
            AddressResponseDTO addressResponseDTO = customerService.updateAddress(users, customerId, addressId, updateAddressByCustomerRequestBodyDTO);
            detailResponseDTO.setMessage("success");
            detailResponseDTO.setData(addressResponseDTO);
            return ResponseEntity.ok(detailResponseDTO);
        } catch (DataNotExistsException e) {
            detailResponseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detailResponseDTO);
        } catch (InvalidDataException e) {
            detailResponseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detailResponseDTO);
        }
    }

    @DeleteMapping("/{customerId}/address/{addressId}")
    public ResponseEntity<DetailCustomerAddressResponseDTO> deleteAddress(
            @AuthenticationPrincipal Users users,
            @PathVariable Long customerId,
            @PathVariable Long addressId
    ) {
        DetailCustomerAddressResponseDTO detailCustomerResponseDTO = new DetailCustomerAddressResponseDTO();

        try {
            customerService.deleteAddress(users, customerId, addressId);
            return ResponseEntity.noContent().build();
        } catch (DataNotExistsException e) {
            detailCustomerResponseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detailCustomerResponseDTO);
        } catch (InvalidDataException e) {
            detailCustomerResponseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detailCustomerResponseDTO);
        }
    }
}
