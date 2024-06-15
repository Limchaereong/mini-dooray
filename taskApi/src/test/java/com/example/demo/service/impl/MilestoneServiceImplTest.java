package com.example.demo.service.impl;

import com.example.demo.dto.MilestoneCreateRequestDto;
import com.example.demo.dto.MilestoneGetByMilestoneIdRequestDto;
import com.example.demo.dto.MilestoneGetDto;
import com.example.demo.dto.MilestoneUpdateRequestDto;
import com.example.demo.entity.Project;
import com.example.demo.entity.Milestone;
import com.example.demo.repository.MilestoneRepository;
import com.example.demo.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MilestoneServiceImplTest {

    @Mock
    private MilestoneRepository milestoneRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private MilestoneServiceImpl milestoneService;

    private Milestone milestone;
    private Project project;

    @BeforeEach
    public void setUp() {
        project = Project.builder()
                .projectId(1L)
                .projectName("Test Project")
                .projectStatus(Project.ProjectStatus.ACTIVE)
                .build();

        milestone = Milestone.builder()
                .milestoneId(1L)
                .milestoneName("Test Milestone")
                .milestoneStartDate(ZonedDateTime.now())
                .milestoneEndDate(ZonedDateTime.now().plusDays(1))
                .project(project)
                .build();
    }

    @Test
    public void testGetMilestonesByProjectId() {
        List<Milestone> milestones = new ArrayList<>();
        milestones.add(milestone);

        when(milestoneRepository.findAllByProjectProjectId(1L)).thenReturn(milestones);

        List<MilestoneGetDto> result = milestoneService.getMilestonesByProjectId(1L);

        assertEquals(1, result.size());
        assertEquals("Test Milestone", result.get(0).milestoneName());
        verify(milestoneRepository, times(1)).findAllByProjectProjectId(1L);
    }

    @Test
    public void testCreateMilestoneById() {
        MilestoneCreateRequestDto createRequest = new MilestoneCreateRequestDto("New Milestone", ZonedDateTime.now(), ZonedDateTime.now().plusDays(1));

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        milestoneService.createMilestoneById(createRequest, 1L);

        ArgumentCaptor<Milestone> milestoneArgumentCaptor = ArgumentCaptor.forClass(Milestone.class);
        verify(milestoneRepository, times(1)).save(milestoneArgumentCaptor.capture());
        assertEquals("New Milestone", milestoneArgumentCaptor.getValue().getMilestoneName());
        assertEquals(project, milestoneArgumentCaptor.getValue().getProject());
    }

    @Test
    public void testCreateMilestoneById_ProjectNotFound() {
        MilestoneCreateRequestDto createRequest = new MilestoneCreateRequestDto("New Milestone", ZonedDateTime.now(), ZonedDateTime.now().plusDays(1));

        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            milestoneService.createMilestoneById(createRequest, 1L);
        });

        assertEquals("Invalid project ID", exception.getMessage());
    }

    @Test
    public void testChangeMilestoneById() {
        MilestoneUpdateRequestDto updateRequest = new MilestoneUpdateRequestDto(1L, "Updated Milestone", ZonedDateTime.now(), ZonedDateTime.now().plusDays(1));

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(milestoneRepository.findById(1L)).thenReturn(Optional.of(milestone));

        milestoneService.changeMilestoneById(updateRequest, 1L);

        verify(milestoneRepository, times(1)).save(milestone);
        assertEquals("Updated Milestone", milestone.getMilestoneName());
    }

    @Test
    public void testChangeMilestoneById_ProjectNotFound() {
        MilestoneUpdateRequestDto updateRequest = new MilestoneUpdateRequestDto(1L, "Updated Milestone", ZonedDateTime.now(), ZonedDateTime.now().plusDays(1));

        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            milestoneService.changeMilestoneById(updateRequest, 1L);
        });

        assertEquals("Invalid project ID", exception.getMessage());
    }

    @Test
    public void testChangeMilestoneById_MilestoneNotFound() {
        MilestoneUpdateRequestDto updateRequest = new MilestoneUpdateRequestDto(1L, "Updated Milestone", ZonedDateTime.now(), ZonedDateTime.now().plusDays(1));

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(milestoneRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            milestoneService.changeMilestoneById(updateRequest, 1L);
        });

        assertEquals("Invalid milestone ID", exception.getMessage());
    }

//    @Test
//    public void testDeleteMilestoneById() {
//        MilestoneGetByMilestoneIdRequestDto deleteRequest = new MilestoneGetByMilestoneIdRequestDto(1L);
//
//        when(milestoneRepository.existsById(1L)).thenReturn(true);
//
//        milestoneService.deleteMilestoneById(deleteRequest);
//
//        verify(milestoneRepository, times(1)).deleteById(1L);
//        verify(milestoneRepository, times(1)).flush();
//    }
//
//    @Test
//    public void testDeleteMilestoneById_MilestoneNotFound() {
//        MilestoneGetByMilestoneIdRequestDto deleteRequest = new MilestoneGetByMilestoneIdRequestDto(1L);
//
//        when(milestoneRepository.existsById(1L)).thenReturn(false);
//
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//            milestoneService.deleteMilestoneById(deleteRequest);
//        });
//
//        assertEquals("Invalid milestone ID", exception.getMessage());
//    }
}
