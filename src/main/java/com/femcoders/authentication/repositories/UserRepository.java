package com.femcoders.authentication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.femcoders.authentication.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
