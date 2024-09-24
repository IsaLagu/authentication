package com.femcoders.authentication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.femcoders.authentication.entities.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);
}
