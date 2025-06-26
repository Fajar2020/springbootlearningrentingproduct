package com.springrent.rent_admin_backend.service;

import com.springrent.rent_admin_backend.dto.rent.DetailRentDetailResponseDTO;
import com.springrent.rent_admin_backend.dto.rent.ListRentResponseDTO;
import com.springrent.rent_admin_backend.dto.rent.UpdateRentDetailRequestBodyDTO;
import com.springrent.rent_admin_backend.exception.DataNotExistsException;
import com.springrent.rent_admin_backend.models.Users;

import java.util.Date;

public interface RentService {
    ListRentResponseDTO getListRent(
            Long customerId,
            Date startDate,
            Date endDate,
            String state
    );
    DetailRentDetailResponseDTO getRentDetailById(
            Long rentId
    ) throws DataNotExistsException;
    DetailRentDetailResponseDTO addRentDetail(
            Users users,
            UpdateRentDetailRequestBodyDTO updateRentDetailRequestBodyDTO
    ) throws DataNotExistsException;
    void updateRentDetail(
            Users users,
            Long rentId,
            UpdateRentDetailRequestBodyDTO updateRentDetailRequestBodyDTO
    ) throws DataNotExistsException;
    DetailRentDetailResponseDTO finishRentDetail(
            Users users,
            Long rentId
    ) throws DataNotExistsException;
}
