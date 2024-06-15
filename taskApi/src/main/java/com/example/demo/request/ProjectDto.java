package com.example.demo.request;

import com.example.demo.entity.Project;

import java.util.List;

public record ProjectDto(String name, Project.ProjectStatus status, Long adminUserId, List<Long> membersId) {
}