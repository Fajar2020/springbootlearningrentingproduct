package com.springrent.rent_admin_backend.service;

import com.springrent.rent_admin_backend.dto.auth.LoginRequestBodyDTO;
import com.springrent.rent_admin_backend.dto.auth.LoginResponseDTO;
import com.springrent.rent_admin_backend.dto.auth.RegistrationRequestBodyDTO;
import com.springrent.rent_admin_backend.dto.auth.UserResponseDTO;
import com.springrent.rent_admin_backend.exception.DataAlreadyExistsException;
import com.springrent.rent_admin_backend.models.Users;

public interface UserService {
    UserResponseDTO addRegisterUser(RegistrationRequestBodyDTO registrationRequestBodyDTO) throws DataAlreadyExistsException;
    LoginResponseDTO loginUser(LoginRequestBodyDTO loginRequestBodyDTO);
    UserResponseDTO getMyInformation(Users users);
}
