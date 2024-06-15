package com.example.demo.service.impl;

import com.example.demo.dto.ProjectCreateDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.MemberPk;
import com.example.demo.entity.Project;
import com.example.demo.dto.ProjectGetDto;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;


    @Override
    public Page<ProjectGetDto> getAllProjectsByUserId(Long userId, Pageable pageable) {
        List<Long> projectIds = memberRepository.findAllByMemberPk_MemberId(userId)
                .stream()
                .map(member -> member.getMemberPk().getProjectId())
                .collect(Collectors.toList());

        if (projectIds.isEmpty()) {
            return Page.empty(pageable);
        }

        return projectRepository.findAllByProjectIdIn(projectIds, pageable)
                .map(project -> {
                    Long adminUserId = memberRepository.findFirstByMemberPk_ProjectIdAndMemberRole(project.getProjectId(), Member.MemberRole.ADMIN)
                            .map(member -> member.getMemberPk().getMemberId())
                            .orElse(null);
                    return ProjectGetDto.builder()
                            .id(project.getProjectId())
                            .name(project.getProjectName())
                            .status(project.getProjectStatus())
                            .adminUserId(adminUserId)
                            .build();
                });
    }

    @Override
    public Project createProject(ProjectCreateDto projectCreateDto) {
        Project project = new Project(null, projectCreateDto.name(), projectCreateDto.status());

        Project savedProject = projectRepository.save(project);

        Member adminMember = new Member(new MemberPk(projectCreateDto.adminUserId(), savedProject.getProjectId()), savedProject, Member.MemberRole.ADMIN);
        memberRepository.save(adminMember);

        List<Member> members = projectCreateDto.memberIds().stream()
                .map(memberId -> new Member(new MemberPk(memberId, savedProject.getProjectId()), savedProject, Member.MemberRole.MEMBER))
                .toList();
        memberRepository.saveAll(members);

        return savedProject;
    }

    @Override
    public void updateProjectStatus(Long projectId, Project.ProjectStatus status) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project Id:" + projectId));
        project.setProjectStatus(status);
        projectRepository.save(project);
    }

    @Override
    public ProjectGetDto getProjectByProjectId(Long projectId) {
        Project project = projectRepository.findFirstByProjectId(projectId);
        return ProjectGetDto.builder()
                .id(project.getProjectId())
                .name(project.getProjectName())
                .status(project.getProjectStatus())
                .build();
    }
}

