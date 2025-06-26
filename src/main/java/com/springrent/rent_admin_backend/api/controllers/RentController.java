package com.springrent.rent_admin_backend.api.controllers;

import com.springrent.rent_admin_backend.dto.rent.DetailRentDetailResponseDTO;
import com.springrent.rent_admin_backend.dto.rent.ListRentResponseDTO;
import com.springrent.rent_admin_backend.dto.rent.UpdateRentDetailRequestBodyDTO;
import com.springrent.rent_admin_backend.models.Users;
import com.springrent.rent_admin_backend.exception.DataNotExistsException;
import com.springrent.rent_admin_backend.service.RentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@AllArgsConstructor
@RestController
@RequestMapping("/rents")
@EnableTransactionManagement
public class RentController {
    private RentService rentService;

    @GetMapping()
    public ResponseEntity<ListRentResponseDTO> getListRent(
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "customerId", required = false) Long customerId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate
    ) {
        return ResponseEntity.ok(rentService.getListRent(
                customerId,
                startDate,
                endDate,
                state
        ));
    }
    @PostMapping()
    public ResponseEntity<DetailRentDetailResponseDTO> addRent(
            @AuthenticationPrincipal Users users,
            @Valid @RequestBody UpdateRentDetailRequestBodyDTO updateRentDetailRequestBodyDTO
    ) {
        DetailRentDetailResponseDTO detailRentResponseDTO = new DetailRentDetailResponseDTO();
        try {
            return ResponseEntity.ok(rentService.addRentDetail(users, updateRentDetailRequestBodyDTO));
        } catch (DataNotExistsException e) {
            detailRentResponseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detailRentResponseDTO);
        }
    }

    @GetMapping("/{rentId}")
    public ResponseEntity<DetailRentDetailResponseDTO> getRentDetailById(@PathVariable Long rentId) {
        DetailRentDetailResponseDTO detailRentResponseDTO = new DetailRentDetailResponseDTO();
        try {
            return ResponseEntity.ok(rentService.getRentDetailById(rentId));
        } catch (DataNotExistsException e) {
            detailRentResponseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detailRentResponseDTO);
        }
    }

    @PatchMapping("/{rentId}")
    public ResponseEntity<DetailRentDetailResponseDTO> updateRentById(
            @AuthenticationPrincipal Users users,
            @PathVariable Long rentId, @Valid @RequestBody UpdateRentDetailRequestBodyDTO updateRentDetailRequestBodyDTO
    ) {
        DetailRentDetailResponseDTO detailRentResponseDTO = new DetailRentDetailResponseDTO();
        try {
            rentService.updateRentDetail(users, rentId, updateRentDetailRequestBodyDTO);
            return ResponseEntity.ok(rentService.getRentDetailById(rentId));
        } catch (DataNotExistsException e) {
            detailRentResponseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detailRentResponseDTO);
        }
    }

    @PatchMapping("/{rentId}/finish")
    public ResponseEntity<DetailRentDetailResponseDTO> finishRentById(
            @AuthenticationPrincipal Users users,
            @PathVariable Long rentId
    ) {
        DetailRentDetailResponseDTO detailRentResponseDTO = new DetailRentDetailResponseDTO();
        try {
            return ResponseEntity.ok(rentService.finishRentDetail(users, rentId));
        } catch (DataNotExistsException e) {
            detailRentResponseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detailRentResponseDTO);
        }
    }
}
