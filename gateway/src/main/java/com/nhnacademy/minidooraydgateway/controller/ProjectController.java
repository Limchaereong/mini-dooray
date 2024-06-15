package com.nhnacademy.minidooraydgateway.controller;

import com.nhnacademy.minidooraydgateway.domain.Project;
import com.nhnacademy.minidooraydgateway.domain.User;
import com.nhnacademy.minidooraydgateway.dto.*;
import com.nhnacademy.minidooraydgateway.exception.LoginRequiredException;
import com.nhnacademy.minidooraydgateway.exception.UserNotFoundException;
import com.nhnacademy.minidooraydgateway.service.ProjectService;
import com.nhnacademy.minidooraydgateway.service.UserService;
import com.nhnacademy.minidooraydgateway.util.SecurityContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final SecurityContextUtil securityContextUtil;

    // 프로젝트 목록 페이지
    @GetMapping
    public String getProjectsPage(@PageableDefault(size = 10) Pageable pageable, Model model) {
        Optional<Long> userIdOpt = securityContextUtil.getCurrentUserId();
        if (userIdOpt.isEmpty()) {
            throw new LoginRequiredException();
        }

        long userId = userIdOpt.get();
        Page<ProjectGetDto> projects = projectService.getAllProjectsByUserId(pageable, userId);

        List<Long> adminUserIds = projects.stream()
                .map(ProjectGetDto::adminUserId)
                .distinct()
                .toList();

        List<ProjectWithAdminEmailDto> projectView = userService.findEmailsByIds(projects, adminUserIds);
        model.addAttribute("projects", new PageImpl<>(projectView, pageable, projects.getTotalElements()));
        return "project/projectList";
    }


    // 프로젝트 상태 변경 페이지
    @GetMapping("/{projectId}/change")
    public String getchangePage(@PathVariable Long projectId, Model model) {
        ProjectGetDto project = projectService.getProjectById(projectId);
        if (project == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "프로젝트를 찾을 수 없습니다.");
        }

        model.addAttribute("project", project);
        model.addAttribute("statuses", Project.Status.values());

        return "project/projectChange";
    }

    // 프로젝트 상태 변경 처리
    @PostMapping("/{projectId}/changeStatus")
    public String change(@PathVariable Long projectId, @RequestParam String status) {
        projectService.updateProjectStatus(projectId, ProjectStatusUpdateDto.builder().status(Project.Status.valueOf(status)).build());
        return "redirect:/projects";
    }


    // 프로젝트 생성 페이지
    @GetMapping("/new")
    public String getNewProjectPage(Model model) {
        model.addAttribute("project", ProjectCreateRequest.builder().memberEmails(new LinkedList<>()).build());
        model.addAttribute("statuses", Project.Status.values());
        return "project/newProject";
    }

    // 프로젝트 생성 처리
    @PostMapping
    public String handleCreateProject(ProjectCreateRequest projectCreateRequest) {

        Optional<Long> userIdOpt = securityContextUtil.getCurrentUserId();
        if (userIdOpt.isEmpty()) {
            throw new LoginRequiredException();
        }
        long userId = userIdOpt.get();

        if (!securityContextUtil.hasAuthority(User.Role.PROJECT_ADMIN.name())) {
            String email = securityContextUtil.getCurrentUserEmail()
                    .orElseThrow(UserNotFoundException::new);
            userService.updateUserRole(Collections.singletonList(email), "PROJECT_ADMIN");
        }

        List<Long> memberIdsByEmails = new LinkedList<>();
        if (projectCreateRequest.memberEmails() != null && !Objects.equals(projectCreateRequest.memberEmails().getFirst(), "")) {
            userService.updateUserRole(projectCreateRequest.memberEmails(), User.Role.PROJECT_MEMBER.name());
            memberIdsByEmails = userService.getUserIdsByEmails(projectCreateRequest.memberEmails());
        }

        ProjectCreateDto projectCreateDto = ProjectCreateDto.builder()
                .name(projectCreateRequest.name())
                .status(projectCreateRequest.status())
                .adminUserId(userId)
                .memberIds(memberIdsByEmails)
                .build();

        projectService.createProject(projectCreateDto);

        return "redirect:/projects";
    }

}
