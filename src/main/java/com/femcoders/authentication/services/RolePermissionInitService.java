package com.femcoders.authentication.services;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.femcoders.authentication.entities.Permission;
import com.femcoders.authentication.entities.Role;
import com.femcoders.authentication.repositories.PermissionRepository;
import com.femcoders.authentication.repositories.RoleRepository;

import jakarta.annotation.PostConstruct;

@Service
public class RolePermissionInitService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @PostConstruct
    public void initRolesAndPermissions() {
        // Crear permisos
        Permission createGroup = new Permission("CREATE_GROUP");
        Permission readGroup = new Permission("READ_GROUP");
        Permission updateGroup = new Permission("UPDATE_GROUP");
        Permission deleteGroup = new Permission("DELETE_GROUP");
        Permission joinGroup = new Permission("JOIN_GROUP");

        Iterable<Permission> permissions = Arrays.asList(createGroup, readGroup, updateGroup, deleteGroup, joinGroup);
        permissionRepository.saveAll(permissions);

        // Crear roles y asignar permisos
        Role superAdmin = new Role("SUPER_ADMIN");
        superAdmin.setPermissions(
                new HashSet<>(Arrays.asList(createGroup, readGroup, updateGroup, deleteGroup, joinGroup)));

        Role creator = new Role("Creator");
        creator.setPermissions(new HashSet<>(Arrays.asList(createGroup, readGroup, updateGroup, deleteGroup)));

        Role user = new Role("User");
        user.setPermissions(new HashSet<>(Arrays.asList(readGroup, joinGroup)));

        roleRepository.saveAll(Arrays.asList(superAdmin, creator, user));
    }
}
