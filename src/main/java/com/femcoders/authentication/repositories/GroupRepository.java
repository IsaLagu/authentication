package com.femcoders.authentication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.femcoders.authentication.entities.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

}
