package com.femcoders.authentication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.femcoders.authentication.dto.LoginRequestDTO;
import com.femcoders.authentication.dto.UserRegistrationDTO;
import com.femcoders.authentication.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok("Login successful");
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm() {
        return new ModelAndView("register"); // Nombre de la vista en la carpeta templates
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        try {
            userService.registerUser(userRegistrationDTO);
            return ResponseEntity.ok("Registration successful.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error during registration: " + e.getMessage());
        }
    }
}
