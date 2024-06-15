package com.nhnacademy.minidooraydgateway.service;

import com.nhnacademy.minidooraydgateway.client.ProjectServiceClient;
import com.nhnacademy.minidooraydgateway.dto.ProjectMemberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ProjectMemberServiceTest {

    @Mock
    private ProjectServiceClient projectServiceClient;

    @InjectMocks
    private ProjectMemberService projectMemberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddMemberToProject() {
        Long projectId = 1L;
        List<Long> memberIds = List.of(1L, 2L, 3L);

        // Using doAnswer instead of doNothing
        doAnswer(invocation -> null).when(projectServiceClient).addMemberToProject(eq(projectId), any(ProjectMemberDto.class));

        projectMemberService.addMemberToProject(projectId, memberIds);

        verify(projectServiceClient, times(1)).addMemberToProject(eq(projectId), any(ProjectMemberDto.class));
    }

    @Test
    void testGetAllProjectMembers() {
        Long projectId = 1L;
        List<Long> expectedMemberIds = List.of(1L, 2L, 3L);

        when(projectServiceClient.getProjectMembers(projectId)).thenReturn(ResponseEntity.ok(expectedMemberIds));

        List<Long> actualMemberIds = projectMemberService.getAllProjectMembers(projectId);

        assertEquals(expectedMemberIds, actualMemberIds);
        verify(projectServiceClient, times(1)).getProjectMembers(projectId);
    }
}
