package com.femcoders.authentication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.femcoders.authentication.dto.GroupDTO;
import com.femcoders.authentication.services.GroupService;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_GROUP')")
    public ResponseEntity<String> createGroup(@RequestBody GroupDTO groupDTO) {
        // Lógica para crear grupo
        return ResponseEntity.ok("Group created successfully");
    }

    @GetMapping("/{groupId}")
    @PreAuthorize("hasAuthority('READ_GROUP')")
    public ResponseEntity<GroupDTO> getGroup(@PathVariable Long groupId) {
        // Lógica para obtener información del grupo
        return ResponseEntity.ok(groupService.getGroupById(groupId));
    }

    @PutMapping("/{groupId}/update")
    @PreAuthorize("hasAuthority('UPDATE_GROUP')")
    public ResponseEntity<String> updateGroup(@PathVariable Long groupId, @RequestBody GroupDTO groupDTO) {
        // Lógica para actualizar grupo
        return ResponseEntity.ok("Group updated successfully");
    }

    @DeleteMapping("/{groupId}/delete")
    @PreAuthorize("hasAuthority('DELETE_GROUP')")
    public ResponseEntity<String> deleteGroup(@PathVariable Long groupId) {
        // Lógica para eliminar grupo
        return ResponseEntity.ok("Group deleted successfully");
    }

    @PostMapping("/{groupId}/join")
    @PreAuthorize("hasAuthority('JOIN_GROUP')")
    public ResponseEntity<String> joinGroup(@PathVariable Long groupId) {
        // Lógica para que un usuario se una a un grupo
        return ResponseEntity.ok("Joined group successfully");
    }
}
