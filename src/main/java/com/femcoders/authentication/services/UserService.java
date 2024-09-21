package com.femcoders.authentication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.femcoders.authentication.DTO.UserDTO;
import com.femcoders.authentication.entities.User;
import com.femcoders.authentication.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerNewUser(UserDTO userDTO) {
        User newUser = new User();
        // Setear atributos del usuario
        return userRepository.save(newUser);
    }
}
