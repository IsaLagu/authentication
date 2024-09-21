package com.femcoders.authentication.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class UserGroupRoleKey implements Serializable {

    private Long userId;
    private Long groupId;
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}
