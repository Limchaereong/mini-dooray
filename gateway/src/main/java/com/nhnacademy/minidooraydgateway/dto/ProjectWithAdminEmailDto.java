package com.nhnacademy.minidooraydgateway.dto;

import com.nhnacademy.minidooraydgateway.domain.Project;
import lombok.Builder;

@Builder
public record ProjectWithAdminEmailDto(Long id, String name, Project.Status status, String adminEmail) {
}
