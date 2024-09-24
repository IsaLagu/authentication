package com.femcoders.authentication.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.femcoders.authentication.dto.UserDTO;
import com.femcoders.authentication.entities.User;
import com.femcoders.authentication.exceptions.EmailExistsException;
import com.femcoders.authentication.repositories.RoleRepository;
import com.femcoders.authentication.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User registerNewUserAccount(UserDTO accountDto) throws EmailExistsException {

        if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException("There is an account with that email address: " + accountDto.getEmail());
        }

        // Create the new user account
        User user = new User();
        user.setName(accountDto.getName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword())); // Aquí se codifica la contraseña
        user.setEmail(accountDto.getEmail());

        // Set the default role
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));

        // Save the new user in the repository
        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
