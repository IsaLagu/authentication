package com.femcoders.authentication.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.femcoders.authentication.entities.Privilege;
import com.femcoders.authentication.entities.Role;
import com.femcoders.authentication.entities.User;
import com.femcoders.authentication.repositories.PrivilegeRepository;
import com.femcoders.authentication.repositories.RoleRepository;
import com.femcoders.authentication.repositories.UserRepository;

import jakarta.transaction.Transactional;
import org.springframework.lang.NonNull;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        Privilege deletePrivilege = createPrivilegeIfNotFound("DELETE_PRIVILEGE");
        Privilege updatePrivilege = createPrivilegeIfNotFound("UPDATE_PRIVILEGE");
        Privilege adminPrivilege = createPrivilegeIfNotFound("ADMIN_PRIVILEGE");
        Privilege manageUsersPrivilege = createPrivilegeIfNotFound("MANAGE_USERS_PRIVILEGE");
        Privilege manageRolesPrivilege = createPrivilegeIfNotFound("MANAGE_ROLES_PRIVILEGE");
        Privilege managePermissionsPrivilege = createPrivilegeIfNotFound("MANAGE_PERMISSIONS_PRIVILEGE");
        Privilege accessReportsPrivilege = createPrivilegeIfNotFound("ACCESS_REPORTS_PRIVILEGE");
        Privilege approveContentPrivilege = createPrivilegeIfNotFound("APPROVE_CONTENT_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege, deletePrivilege, updatePrivilege, adminPrivilege, manageUsersPrivilege,
                manageRolesPrivilege, managePermissionsPrivilege, accessReportsPrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));
        createRoleIfNotFound("ROLE_MODERATOR", Arrays.asList(readPrivilege, approveContentPrivilege));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        User user = new User();
        user.setName("TestAdmin");
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("testadmin@test.com");
        user.setRoles(Arrays.asList(adminRole));
        user.setEnabled(true);
        userRepository.save(user);

        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
