package com.femcoders.authentication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.femcoders.authentication.dto.GroupDTO;
import com.femcoders.authentication.entities.Group;
import com.femcoders.authentication.entities.User;
import com.femcoders.authentication.repositories.GroupRepository;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Group createGroup(GroupDTO groupDTO, User creator) {
        Group newGroup = new Group();
        newGroup.setGroupName(groupDTO.getGroupName());
        newGroup.setCreatedBy(creator);
        return groupRepository.save(newGroup);
    }

    public GroupDTO getGroupById(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        return new GroupDTO(group.getGroupName(), group.getCreatedBy().getUsername());
    }

    // Otros métodos para actualizar y eliminar grupos
}
