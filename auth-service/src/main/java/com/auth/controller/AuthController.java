package com.auth.controller;

import com.auth.dto.LoginRequestDto;
import com.auth.dto.LoginResponseDto;

import com.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Generate token on user Login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {

        Optional<String> tokenOptional = authService.authenticateAndGenerateToken(loginRequestDto);
        if (tokenOptional.isPresent()) {
            LoginResponseDto loginResponseDto = new LoginResponseDto(tokenOptional.get());
            return ResponseEntity.ok(loginResponseDto);
        } else {
            System.out.println("Authentication failed for user: " + loginRequestDto.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
