package com.nhnacademy.minidooraydgateway.service;

import com.nhnacademy.minidooraydgateway.client.ProjectServiceClient;
import com.nhnacademy.minidooraydgateway.domain.Project;
import com.nhnacademy.minidooraydgateway.dto.ProjectCreateDto;
import com.nhnacademy.minidooraydgateway.dto.ProjectGetDto;
import com.nhnacademy.minidooraydgateway.dto.ProjectStatusUpdateDto;
import com.nhnacademy.minidooraydgateway.util.PaginationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProjectServiceTest {

    @Mock
    private ProjectServiceClient projectServiceClient;

    @InjectMocks
    private ProjectService projectService;

    private ProjectGetDto projectGetDto;
    private ProjectCreateDto projectCreateDto;
    private ProjectStatusUpdateDto projectStatusUpdateDto;
    private Pageable pageable;
    private Page<ProjectGetDto> projectPage;

    @BeforeEach
    void setUp() {
        projectGetDto = ProjectGetDto.builder()
                .id(1L)
                .name("Test Project")
                .status(Project.Status.ACTIVE)
                .adminUserId(1L)
                .build();

        projectCreateDto = ProjectCreateDto.builder()
                .name("New Project")
                .status(Project.Status.ACTIVE)
                .adminUserId(1L)
                .memberIds(Collections.singletonList(2L))
                .build();

        projectStatusUpdateDto = ProjectStatusUpdateDto.builder()
                .status(Project.Status.ENDED)
                .build();

        pageable = PageRequest.of(0, 10);
        projectPage = new PageImpl<>(Collections.singletonList(projectGetDto), pageable, 1);
    }

    @Test
    void testGetProjectById() {
        when(projectServiceClient.getProjectById(1L)).thenReturn(ResponseEntity.ok(projectGetDto));

        ProjectGetDto result = projectService.getProjectById(1L);

        assertNotNull(result);
        assertEquals(projectGetDto.id(), result.id());
        assertEquals(projectGetDto.name(), result.name());
        assertEquals(projectGetDto.status(), result.status());
        assertEquals(projectGetDto.adminUserId(), result.adminUserId());
    }

    @Test
    void testCreateProject() {
        when(projectServiceClient.createProject(any(ProjectCreateDto.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());

        projectService.createProject(projectCreateDto);

        verify(projectServiceClient, times(1)).createProject(any(ProjectCreateDto.class));
    }

    @Test
    void testGetAllProjectsByUserId() {
        when(projectServiceClient.getAllProjectsByUserId(anyLong(), anyInt(), anyInt(), any()))
                .thenReturn(ResponseEntity.ok(projectPage));

        Page<ProjectGetDto> result = projectService.getAllProjectsByUserId(pageable, 1L);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(projectGetDto.id(), result.getContent().get(0).id());
    }

    @Test
    void testUpdateProjectStatus() {
        when(projectServiceClient.updateProjectStatus(anyLong(), any(ProjectStatusUpdateDto.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).build());

        assertDoesNotThrow(() -> projectService.updateProjectStatus(1L, projectStatusUpdateDto));

        verify(projectServiceClient, times(1)).updateProjectStatus(anyLong(), any(ProjectStatusUpdateDto.class));
    }

    @Test
    void testUpdateProjectStatus_Failure() {
        when(projectServiceClient.updateProjectStatus(anyLong(), any(ProjectStatusUpdateDto.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> projectService.updateProjectStatus(1L, projectStatusUpdateDto));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
    }

    // Test for ProjectGetDto
    @Test
    void testProjectGetDto() {
        ProjectGetDto dto = new ProjectGetDto(1L, "Project Name", Project.Status.ACTIVE, 100L);

        assertEquals(1L, dto.id());
        assertEquals("Project Name", dto.name());
        assertEquals(Project.Status.ACTIVE, dto.status());
        assertEquals(100L, dto.adminUserId());
    }
}
