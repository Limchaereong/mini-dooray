package com.nhnacademy.minidooraydgateway.domain;

import lombok.Data;

@Data
public class ProjectMember {
    private Long userId;
    private Long projectId;
    private Role role;

    public enum Role {
        ADMIN, MEMBER;
    }
}
