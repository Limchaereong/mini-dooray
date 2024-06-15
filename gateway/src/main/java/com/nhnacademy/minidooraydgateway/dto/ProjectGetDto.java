package com.nhnacademy.minidooraydgateway.dto;

import com.nhnacademy.minidooraydgateway.domain.Project;
import lombok.Builder;

@Builder
public record ProjectGetDto(Long id, String name, Project.Status status, Long adminUserId) {
}