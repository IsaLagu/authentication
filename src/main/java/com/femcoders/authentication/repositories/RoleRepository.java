package com.femcoders.authentication.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.femcoders.authentication.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Puedes agregar métodos personalizados si lo necesitas.

    // Por ejemplo, encontrar roles por su nombre:
    Optional<Role> findByRoleName(String roleName);
}
