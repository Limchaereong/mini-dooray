package com.example.demo.dto;

import com.example.demo.entity.Project;
import lombok.Builder;

import java.util.List;

@Builder
public record ProjectCreateDto(String name, Project.ProjectStatus status, Long adminUserId, List<Long> memberIds) {
}