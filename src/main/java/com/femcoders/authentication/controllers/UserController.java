package com.femcoders.authentication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.femcoders.authentication.dto.UserDTO;
import com.femcoders.authentication.exceptions.EmailExistsException;
import com.femcoders.authentication.services.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
            userService.registerNewUserAccount(userDTO);
            return ResponseEntity.ok("User registered successfully!");
        } catch (EmailExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
