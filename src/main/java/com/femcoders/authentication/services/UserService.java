package com.femcoders.authentication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.femcoders.authentication.dto.UserRegistrationDTO;
import com.femcoders.authentication.entities.Role;
import com.femcoders.authentication.entities.User;
import com.femcoders.authentication.repositories.RoleRepository;
import com.femcoders.authentication.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    // Registro de usuario
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setEnabled(true);

        // Asignar rol de usuario por defecto
        Role userRole = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.getRoles().add(userRole); // Agregar el rol al usuario
        // No es necesario userRepository.save(userRole) si tienes cascade

        userRepository.save(user); // Guardar el usuario y los roles (gracias a CascadeType.PERSIST)
    }
}
