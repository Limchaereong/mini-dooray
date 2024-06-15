package com.example.demo.service.impl;

import com.example.demo.dto.ProjectCreateDto;
import com.example.demo.dto.ProjectGetDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.MemberPk;
import com.example.demo.entity.Project;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private Project project;
    private Member member;

    @BeforeEach
    public void setUp() {
        project = Project.builder()
                .projectId(1L)
                .projectName("Test Project")
                .projectStatus(Project.ProjectStatus.ACTIVE)
                .build();

        MemberPk memberPk = MemberPk.builder()
                .memberId(1L)
                .projectId(1L)
                .build();

        member = new Member(memberPk, project, Member.MemberRole.ADMIN);
    }

    @Test
    public void testGetAllProjectsByUserId() {
        Pageable pageable = PageRequest.of(0, 10);

        when(memberRepository.findAllByMemberPk_MemberId(1L)).thenReturn(List.of(member));
        when(projectRepository.findAllByProjectIdIn(List.of(1L), pageable))
                .thenReturn(new PageImpl<>(List.of(project)));
        when(memberRepository.findFirstByMemberPk_ProjectIdAndMemberRole(1L, Member.MemberRole.ADMIN))
                .thenReturn(Optional.of(member));

        Page<ProjectGetDto> result = projectService.getAllProjectsByUserId(1L, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Test Project", result.getContent().get(0).name());
        verify(memberRepository, times(1)).findAllByMemberPk_MemberId(1L);
        verify(projectRepository, times(1)).findAllByProjectIdIn(List.of(1L), pageable);
    }

    @Test
    public void testGetAllProjectsByUserId_NoProjects() {
        Pageable pageable = PageRequest.of(0, 10);

        when(memberRepository.findAllByMemberPk_MemberId(1L)).thenReturn(Collections.emptyList());

        Page<ProjectGetDto> result = projectService.getAllProjectsByUserId(1L, pageable);

        assertTrue(result.isEmpty());
        verify(memberRepository, times(1)).findAllByMemberPk_MemberId(1L);
        verify(projectRepository, never()).findAllByProjectIdIn(any(), eq(pageable));
    }

    @Test
    public void testCreateProject() {
        ProjectCreateDto createDto = new ProjectCreateDto("Test Project", Project.ProjectStatus.ACTIVE, 1L, List.of(2L, 3L));

        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        Project result = projectService.createProject(createDto);

        assertEquals("Test Project", result.getProjectName());
        verify(projectRepository, times(1)).save(any(Project.class));
        verify(memberRepository, times(1)).save(any(Member.class));
        verify(memberRepository, times(1)).saveAll(anyList());
    }

    @Test
    public void testUpdateProjectStatus() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        projectService.updateProjectStatus(1L, Project.ProjectStatus.DORMANT);

        assertEquals(Project.ProjectStatus.DORMANT, project.getProjectStatus());
        verify(projectRepository, times(1)).findById(1L);
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    public void testUpdateProjectStatus_ProjectNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.updateProjectStatus(1L, Project.ProjectStatus.DORMANT);
        });

        assertEquals("Invalid project Id:1", exception.getMessage());
        verify(projectRepository, times(1)).findById(1L);
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    public void testGetProjectByProjectId() {
        when(projectRepository.findFirstByProjectId(1L)).thenReturn(project);

        ProjectGetDto result = projectService.getProjectByProjectId(1L);

        assertNotNull(result);
        assertEquals("Test Project", result.name());
        verify(projectRepository, times(1)).findFirstByProjectId(1L);
    }
}
