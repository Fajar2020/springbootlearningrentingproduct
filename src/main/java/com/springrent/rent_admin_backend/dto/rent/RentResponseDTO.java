package com.springrent.rent_admin_backend.dto.rent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RentResponseDTO {
    private Long id;
    private String state;
    private Double totalAmount;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
