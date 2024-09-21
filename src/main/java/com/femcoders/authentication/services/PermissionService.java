package com.femcoders.authentication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.femcoders.authentication.entities.Permission;
import com.femcoders.authentication.repositories.PermissionRepository;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public Permission getPermissionByName(String permissionName) {
        return permissionRepository.findByPermissionName(permissionName)
                .orElseThrow(() -> new RuntimeException("Permission not found"));
    }
}
