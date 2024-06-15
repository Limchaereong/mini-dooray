package com.example.demo.controller;

import com.example.demo.dto.ProjectCreateDto;
import com.example.demo.dto.ProjectGetDto;
import com.example.demo.dto.ProjectStatusUpdateDto;
import com.example.demo.entity.Project;
import com.example.demo.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProjectsByUserId() {
        // Arrange
        Long userId = 1L;
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<ProjectGetDto> projectsList = Arrays.asList(
                new ProjectGetDto(1L, "Project1", Project.ProjectStatus.ACTIVE, userId),
                new ProjectGetDto(2L, "Project2", Project.ProjectStatus.DORMANT, userId)
        );
        Page<ProjectGetDto> projectsPage = new PageImpl<>(projectsList, pageable, projectsList.size());
        when(projectService.getAllProjectsByUserId(eq(userId), any(Pageable.class))).thenReturn(projectsPage);

        // Act
        ResponseEntity<Page<ProjectGetDto>> response = projectController.getAllProjectsByUserId(userId, page, size, null);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(projectsPage);
        verify(projectService, times(1)).getAllProjectsByUserId(eq(userId), any(Pageable.class));
    }

    @Test
    void createProject() {
        // Arrange
        ProjectCreateDto projectCreateDto = ProjectCreateDto.builder()
                .name("New Project")
                .status(Project.ProjectStatus.ACTIVE)
                .adminUserId(1L)
                .memberIds(Arrays.asList(2L, 3L))
                .build();
        Project project = new Project(1L, "New Project", Project.ProjectStatus.ACTIVE);
        when(projectService.createProject(any(ProjectCreateDto.class))).thenReturn(project);

        // Act
        ResponseEntity<Project> response = projectController.createProject(projectCreateDto);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(project);
        verify(projectService, times(1)).createProject(any(ProjectCreateDto.class));
    }

    @Test
    void updateProjectStatus() {
        // Arrange
        Long projectId = 1L;
        ProjectStatusUpdateDto projectStatusUpdateDto = new ProjectStatusUpdateDto(Project.ProjectStatus.DORMANT);

        // Act
        ResponseEntity<Void> response = projectController.updateProjectStatus(projectId, projectStatusUpdateDto);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(projectService, times(1)).updateProjectStatus(projectId, projectStatusUpdateDto.status());
    }

    @Test
    void getProjectById() {
        // Arrange
        Long projectId = 1L;
        ProjectGetDto projectGetDto = new ProjectGetDto(projectId, "Project Name", Project.ProjectStatus.ACTIVE, 1L);
        when(projectService.getProjectByProjectId(projectId)).thenReturn(projectGetDto);

        // Act
        ResponseEntity<ProjectGetDto> response = projectController.getProjectById(projectId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(projectGetDto);
        verify(projectService, times(1)).getProjectByProjectId(projectId);
    }
}
