package com.springrent.rent_admin_backend.api.controllers;

import com.springrent.rent_admin_backend.dto.auth.*;
import com.springrent.rent_admin_backend.models.Users;
import com.springrent.rent_admin_backend.exception.DataAlreadyExistsException;
import com.springrent.rent_admin_backend.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<DetailUserResponseDTO> register(@Valid @RequestBody RegistrationRequestBodyDTO registrationRequestBodyDTO) {
        DetailUserResponseDTO detailUserResponseDTO = new DetailUserResponseDTO();
        try {
            UserResponseDTO userResponseDTO = userService.addRegisterUser(registrationRequestBodyDTO);
            detailUserResponseDTO.setMessage("success");
            detailUserResponseDTO.setData(userResponseDTO);
            return ResponseEntity.ok(detailUserResponseDTO);
        } catch (DataAlreadyExistsException e) {
            detailUserResponseDTO.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detailUserResponseDTO);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<DetailLoginResponseDTO> login(@Valid @RequestBody LoginRequestBodyDTO loginRequestBodyDTO) {
        DetailLoginResponseDTO detailLoginResponseDTO = new DetailLoginResponseDTO();
        LoginResponseDTO loginResponseDTO = userService.loginUser(loginRequestBodyDTO);

        if (loginResponseDTO == null) {
            detailLoginResponseDTO.setMessage("fail");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(detailLoginResponseDTO);
        }

        detailLoginResponseDTO.setMessage("success");
        detailLoginResponseDTO.setData(loginResponseDTO);
        return ResponseEntity.ok(detailLoginResponseDTO);
    }

    @GetMapping("/me")
    public ResponseEntity<DetailUserResponseDTO> getMyInformation(@AuthenticationPrincipal Users users) {

        DetailUserResponseDTO detailUserResponseDTO = new DetailUserResponseDTO();
        if (users == null) {
            detailUserResponseDTO.setMessage("fail");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(detailUserResponseDTO);
        }

        detailUserResponseDTO.setMessage("success");
        detailUserResponseDTO.setData(userService.getMyInformation(users));
        return ResponseEntity.ok(detailUserResponseDTO);
    }
}
