package com.springrent.rent_admin_backend.service.impl;

import com.springrent.rent_admin_backend.dto.auth.LoginRequestBodyDTO;
import com.springrent.rent_admin_backend.dto.auth.LoginResponseDTO;
import com.springrent.rent_admin_backend.dto.auth.RegistrationRequestBodyDTO;
import com.springrent.rent_admin_backend.dto.auth.UserResponseDTO;
import com.springrent.rent_admin_backend.models.Users;
import com.springrent.rent_admin_backend.repository.UsersRepository;
import com.springrent.rent_admin_backend.exception.DataAlreadyExistsException;
import com.springrent.rent_admin_backend.security.EncryptionService;
import com.springrent.rent_admin_backend.security.JWTService;
import com.springrent.rent_admin_backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

import static com.springrent.rent_admin_backend.mapper.UsersMapper.mapToDetailUserResponseDTO;


@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private UsersRepository usersRepository;
    private EncryptionService encryptionService;
    private JWTService jwtService;

    @Override
    public UserResponseDTO addRegisterUser(
            RegistrationRequestBodyDTO registrationRequestBodyDTO
    ) throws DataAlreadyExistsException {

        Optional<Users> optionalUsers = usersRepository.findByUsernameIgnoreCaseAndIsDeletedFalse(registrationRequestBodyDTO.getUsername());

        if (optionalUsers.isPresent()) {
            throw new DataAlreadyExistsException("Users username");
        }

        optionalUsers = usersRepository.findByEmailIgnoreCaseAndIsDeletedFalse(registrationRequestBodyDTO.getEmail());

        if (optionalUsers.isPresent()) {
            throw new DataAlreadyExistsException("Users email");
        }

        Timestamp now = new Timestamp(System.currentTimeMillis());
        Users users = new Users();
        users.setEmail(registrationRequestBodyDTO.getEmail());
        users.setEmployeeNumber(registrationRequestBodyDTO.getEmployeeNumber());
        users.setUsername(registrationRequestBodyDTO.getUsername());
        users.setIsDeleted(false);
        users.setCreatedAt(now);
        users.setUpdatedAt(now);
        users.setPassword(encryptionService.encryptPassword(registrationRequestBodyDTO.getPassword()));
        users = usersRepository.save(users);

        return new UserResponseDTO(
                users.getId(),
                users.getUsername(),
                users.getEmployeeNumber(),
                users.getEmail()
        );
    }

    @Override
    public LoginResponseDTO loginUser(LoginRequestBodyDTO loginRequestBodyDTO) {
        Optional<Users> optionalUsers = usersRepository.findByUsernameIgnoreCaseAndIsDeletedFalse(loginRequestBodyDTO.getUsername());

        if (optionalUsers.isPresent()) {
            Users users = optionalUsers.get();
            if (encryptionService.verifyPassword(loginRequestBodyDTO.getPassword(), users.getPassword())) {
                return new LoginResponseDTO(jwtService.generateJWT(users));
            }
        }

        return null;
    }

    @Override
    public UserResponseDTO getMyInformation(Users users) {
        return mapToDetailUserResponseDTO(users);
    }

}
