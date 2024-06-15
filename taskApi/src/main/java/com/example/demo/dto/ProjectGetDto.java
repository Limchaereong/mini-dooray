package com.example.demo.dto;

import com.example.demo.entity.Project;
import lombok.Builder;

@Builder
public record ProjectGetDto(Long id, String name, Project.ProjectStatus status, Long adminUserId) {
}