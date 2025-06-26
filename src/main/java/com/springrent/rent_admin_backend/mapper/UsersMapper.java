package com.springrent.rent_admin_backend.mapper;

import com.springrent.rent_admin_backend.dto.auth.UserResponseDTO;
import com.springrent.rent_admin_backend.models.Users;

public class UsersMapper {

    public static UserResponseDTO mapToDetailUserResponseDTO(Users users) {
        return new UserResponseDTO(
                users.getId(),
                users.getUsername(),
                users.getEmployeeNumber(),
                users.getEmail()
        );
    }
}
