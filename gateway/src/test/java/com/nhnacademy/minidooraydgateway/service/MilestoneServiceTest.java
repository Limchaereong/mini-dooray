package com.nhnacademy.minidooraydgateway.service;

import com.nhnacademy.minidooraydgateway.client.ProjectServiceClient;
import com.nhnacademy.minidooraydgateway.dto.MilestoneCreateRequestDto;
import com.nhnacademy.minidooraydgateway.dto.MilestoneGetByMilestoneIdRequestDto;
import com.nhnacademy.minidooraydgateway.dto.MilestoneGetDto;
import com.nhnacademy.minidooraydgateway.dto.MilestoneUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MilestoneServiceTest {

    @Mock
    private ProjectServiceClient projectServiceClient;

    @InjectMocks
    private MilestoneService milestoneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMilestonesByProjectId() {
        Long projectId = 1L;
        List<MilestoneGetDto> expectedMilestones = List.of(
                MilestoneGetDto.builder()
                        .milestoneId(1L)
                        .milestoneName("Milestone 1")
                        .milestoneStartDate(ZonedDateTime.now())
                        .milestoneEndDate(ZonedDateTime.now().plusDays(7))
                        .build(),
                MilestoneGetDto.builder()
                        .milestoneId(2L)
                        .milestoneName("Milestone 2")
                        .milestoneStartDate(ZonedDateTime.now())
                        .milestoneEndDate(ZonedDateTime.now().plusDays(7))
                        .build()
        );

        when(projectServiceClient.getProjectMilestones(projectId)).thenReturn(ResponseEntity.ok(expectedMilestones));

        List<MilestoneGetDto> actualMilestones = milestoneService.getMilestonesByProjectId(projectId);

        assertEquals(expectedMilestones, actualMilestones);
        verify(projectServiceClient, times(1)).getProjectMilestones(projectId);
    }

    @Test
    void testCreateMilestone() {
        Long projectId = 1L;
        MilestoneCreateRequestDto request = MilestoneCreateRequestDto.builder()
                .milestoneName("Milestone 1")
                .milestoneStartDate(ZonedDateTime.now())
                .milestoneEndDate(ZonedDateTime.now().plusDays(7))
                .build();

        doAnswer(invocation -> null).when(projectServiceClient).createMilestone(any(Long.class), any(MilestoneCreateRequestDto.class));

        milestoneService.createMilestone(projectId, request);

        verify(projectServiceClient, times(1)).createMilestone(projectId, request);
    }

    @Test
    void testUpdateMilestone() {
        Long projectId = 1L;
        MilestoneUpdateRequestDto request = MilestoneUpdateRequestDto.builder()
                .milestoneId(1L)
                .milestoneName("Updated Milestone")
                .milestoneStartDate(ZonedDateTime.now())
                .milestoneEndDate(ZonedDateTime.now().plusDays(7))
                .build();

        doAnswer(invocation -> null).when(projectServiceClient).updateMilestone(any(Long.class), any(MilestoneUpdateRequestDto.class));

        milestoneService.updateMilestone(projectId, request);

        verify(projectServiceClient, times(1)).updateMilestone(projectId, request);
    }

    @Test
    void testDeleteMilestone() {
        Long projectId = 1L;
        MilestoneGetByMilestoneIdRequestDto request = new MilestoneGetByMilestoneIdRequestDto(1L);

        doAnswer(invocation -> null).when(projectServiceClient).deleteMilestone(any(Long.class), any(MilestoneGetByMilestoneIdRequestDto.class));

        milestoneService.deleteMilestone(projectId, request);

        verify(projectServiceClient, times(1)).deleteMilestone(projectId, request);
    }
}
