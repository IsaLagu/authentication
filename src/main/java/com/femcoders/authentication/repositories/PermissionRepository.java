package com.femcoders.authentication.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.femcoders.authentication.entities.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByPermissionName(String permissionName);
}
