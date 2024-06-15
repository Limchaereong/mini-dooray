package com.example.demo.dto;

import com.example.demo.entity.Project;

public record ProjectStatusUpdateDto(Project.ProjectStatus status) {
}
