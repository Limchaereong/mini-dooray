package com.example.demo.service;

import com.example.demo.dto.ProjectCreateDto;
import com.example.demo.dto.ProjectGetByUserIdRequestDto;
import com.example.demo.dto.ProjectGetDto;
import com.example.demo.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    Page<ProjectGetDto> getAllProjectsByUserId(Long userId, Pageable pageable);

    Project createProject(ProjectCreateDto projectCreateDto);

    void updateProjectStatus(Long projectId, Project.ProjectStatus status);

    ProjectGetDto getProjectByProjectId(Long projectId);
}
