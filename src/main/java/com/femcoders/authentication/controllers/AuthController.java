package com.femcoders.authentication.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.femcoders.authentication.DTO.LoginRequestDTO;
import com.femcoders.authentication.DTO.UserRegistrationDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        // Lógica de autenticación (se puede usar Spring Security)
        return ResponseEntity.ok("Login successful");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationDTO userDTO) {
        // Lógica para registrar un nuevo usuario
        return ResponseEntity.ok("User registered successfully");
    }
}
