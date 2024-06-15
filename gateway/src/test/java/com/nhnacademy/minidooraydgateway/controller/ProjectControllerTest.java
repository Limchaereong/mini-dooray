package com.nhnacademy.minidooraydgateway.controller;

import com.nhnacademy.minidooraydgateway.domain.Project;
import com.nhnacademy.minidooraydgateway.domain.User;
import com.nhnacademy.minidooraydgateway.dto.*;
import com.nhnacademy.minidooraydgateway.exception.LoginRequiredException;
import com.nhnacademy.minidooraydgateway.service.ProjectService;
import com.nhnacademy.minidooraydgateway.service.UserService;
import com.nhnacademy.minidooraydgateway.util.SecurityContextUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @Mock
    private UserService userService;

    @Mock
    private SecurityContextUtil securityContextUtil;

    @Mock
    private Model model;

    @InjectMocks
    private ProjectController projectController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(projectController)
                .setControllerAdvice(new ExceptionHandlerController())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void testGetProjectsPage() throws Exception {
        Long userId = 1L;
        List<ProjectGetDto> projectList = Collections.singletonList(
                ProjectGetDto.builder()
                        .id(1L)
                        .name("Test Project")
                        .status(Project.Status.ACTIVE)
                        .adminUserId(1L)
                        .build());
        Page<ProjectGetDto> projects = new PageImpl<>(projectList);
        List<Long> adminUserIds = List.of(1L);
        List<ProjectWithAdminEmailDto> projectView = Collections.singletonList(
                ProjectWithAdminEmailDto.builder()
                        .id(1L)
                        .name("Test Project")
                        .status(Project.Status.ACTIVE)
                        .adminEmail("admin@test.com")
                        .build());

        when(securityContextUtil.getCurrentUserId()).thenReturn(Optional.of(userId));
        when(projectService.getAllProjectsByUserId(any(Pageable.class), eq(userId))).thenReturn(projects);
        when(userService.findEmailsByIds(eq(projects), eq(adminUserIds))).thenReturn(projectView);

        mockMvc.perform(get("/projects")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("project/projectList"))
                .andExpect(model().attribute("projects", new PageImpl<>(projectView, PageRequest.of(0, 10), projects.getTotalElements())));

        verify(securityContextUtil).getCurrentUserId();
        verify(projectService).getAllProjectsByUserId(any(Pageable.class), eq(userId));
        verify(userService).findEmailsByIds(eq(projects), eq(adminUserIds));
    }

    @Test
    public void testGetProjectsPage_LoginRequiredException() throws Exception {
        when(securityContextUtil.getCurrentUserId()).thenReturn(Optional.empty());

        mockMvc.perform(get("/projects"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(securityContextUtil).getCurrentUserId();
        verifyNoMoreInteractions(projectService, userService);
    }

    @Test
    public void testGetChangePage() throws Exception {
        Long projectId = 1L;
        ProjectGetDto project = ProjectGetDto.builder()
                .id(projectId)
                .name("Test Project")
                .status(Project.Status.ACTIVE)
                .adminUserId(1L)
                .build();

        when(projectService.getProjectById(projectId)).thenReturn(project);

        mockMvc.perform(get("/projects/1/change"))
                .andExpect(status().isOk())
                .andExpect(view().name("project/projectChange"))
                .andExpect(model().attribute("project", project))
                .andExpect(model().attribute("statuses", Project.Status.values()));

        verify(projectService).getProjectById(projectId);
    }

    @Test
    public void testGetChangePage_ProjectNotFound() throws Exception {
        Long projectId = 1L;

        when(projectService.getProjectById(projectId)).thenReturn(null);

        mockMvc.perform(get("/projects/1/change"))
                .andExpect(status().isNotFound());

        verify(projectService).getProjectById(projectId);
    }

    @Test
    public void testChangeProjectStatus() throws Exception {
        Long projectId = 1L;
        String status = "ACTIVE";

        mockMvc.perform(post("/projects/1/changeStatus")
                        .param("status", status))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));

        verify(projectService).updateProjectStatus(eq(projectId), any(ProjectStatusUpdateDto.class));
    }

    @Test
    public void testGetNewProjectPage() throws Exception {
        mockMvc.perform(get("/projects/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("project/newProject"))
                .andExpect(model().attributeExists("project"))
                .andExpect(model().attribute("statuses", Project.Status.values()));
    }

    @Test
    public void testHandleCreateProject() throws Exception {
        Long userId = 1L;
        ProjectCreateRequest projectCreateRequest = ProjectCreateRequest.builder()
                .name("New Project")
                .status(Project.Status.ACTIVE)
                .memberEmails(new LinkedList<>())
                .build();

        when(securityContextUtil.getCurrentUserId()).thenReturn(Optional.of(userId));
        when(securityContextUtil.hasAuthority(User.Role.PROJECT_ADMIN.name())).thenReturn(true);

        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", projectCreateRequest.name())
                        .param("status", projectCreateRequest.status().name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));

        verify(securityContextUtil).getCurrentUserId();
        verify(securityContextUtil).hasAuthority(User.Role.PROJECT_ADMIN.name());
        verify(projectService).createProject(any(ProjectCreateDto.class));
    }

    @Test
    public void testHandleCreateProject_LoginRequiredException() throws Exception {
        when(securityContextUtil.getCurrentUserId()).thenReturn(Optional.empty());

        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "New Project")
                        .param("status", Project.Status.ACTIVE.name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(securityContextUtil).getCurrentUserId();
        verifyNoMoreInteractions(projectService, userService);
    }

    @Test
    public void testHandleCreateProject_UserRoleUpdate() throws Exception {
        Long userId = 1L;
        String email = "user@test.com";
        ProjectCreateRequest projectCreateRequest = ProjectCreateRequest.builder()
                .name("New Project")
                .status(Project.Status.ACTIVE)
                .memberEmails(new LinkedList<>())
                .build();

        when(securityContextUtil.getCurrentUserId()).thenReturn(Optional.of(userId));
        when(securityContextUtil.hasAuthority(User.Role.PROJECT_ADMIN.name())).thenReturn(false);
        when(securityContextUtil.getCurrentUserEmail()).thenReturn(Optional.of(email));

        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", projectCreateRequest.name())
                        .param("status", projectCreateRequest.status().name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));

        verify(securityContextUtil).getCurrentUserId();
        verify(securityContextUtil).hasAuthority(User.Role.PROJECT_ADMIN.name());
        verify(securityContextUtil).getCurrentUserEmail();
        verify(userService).updateUserRole(eq(Collections.singletonList(email)), eq("PROJECT_ADMIN"));
        verify(projectService).createProject(any(ProjectCreateDto.class));
    }

    @Test
    public void testHandleCreateProject_UserRoleUpdate_EmailNotFound() throws Exception {
        Long userId = 1L;
        ProjectCreateRequest projectCreateRequest = ProjectCreateRequest.builder()
                .name("New Project")
                .status(Project.Status.ACTIVE)
                .memberEmails(new LinkedList<>())
                .build();

        when(securityContextUtil.getCurrentUserId()).thenReturn(Optional.of(userId));
        when(securityContextUtil.hasAuthority(User.Role.PROJECT_ADMIN.name())).thenReturn(false);
        when(securityContextUtil.getCurrentUserEmail()).thenReturn(Optional.of("user@example.com"));

        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", projectCreateRequest.name())
                        .param("status", projectCreateRequest.status().name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));

        verify(securityContextUtil).getCurrentUserId();
        verify(securityContextUtil).hasAuthority(User.Role.PROJECT_ADMIN.name());
        verify(securityContextUtil).getCurrentUserEmail();
        verify(userService).updateUserRole(Collections.singletonList("user@example.com"), "PROJECT_ADMIN");
        verify(projectService).createProject(any(ProjectCreateDto.class));
        verifyNoMoreInteractions(securityContextUtil, userService, projectService);
    }
}

@ControllerAdvice
class ExceptionHandlerController {

    @ExceptionHandler(LoginRequiredException.class)
    public ModelAndView handleLoginRequiredException(LoginRequiredException ex) {
        return new ModelAndView("redirect:/login");
    }
}
