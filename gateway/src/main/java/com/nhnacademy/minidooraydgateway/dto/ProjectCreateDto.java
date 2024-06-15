package com.nhnacademy.minidooraydgateway.dto;

import com.nhnacademy.minidooraydgateway.domain.Project;
import lombok.Builder;

import java.util.List;

@Builder
public record ProjectCreateDto(String name, Project.Status status, Long adminUserId, List<Long> memberIds) {
}