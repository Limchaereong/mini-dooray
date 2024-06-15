package com.nhnacademy.minidooraydgateway.controller;

import com.nhnacademy.minidooraydgateway.service.ProjectMemberService;
import com.nhnacademy.minidooraydgateway.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectMemberControllerTest {

    @Mock
    private ProjectMemberService projectMemberService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ProjectMemberController projectMemberController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(projectMemberController).build();
    }

    @Test
    public void testHandleAddProjectMember_Success() throws Exception {
        Long projectId = 1L;
        List<String> memberEmails = Arrays.asList("user1@example.com", "user2@example.com");
        List<Long> memberIds = Arrays.asList(2L, 3L);

        when(userService.getUserIdsByEmails(memberEmails)).thenReturn(memberIds);

        mockMvc.perform(post("/projects/{projectId}/members", projectId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("memberEmails", memberEmails.toArray(new String[0])))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/" + projectId + "/members"));

        verify(userService).getUserIdsByEmails(memberEmails);
        verify(projectMemberService).addMemberToProject(projectId, memberIds);
        verify(userService).updateUserRole(memberEmails, "PROJECT_MEMBER");
    }

    @Test
    public void testHandleAddProjectMember_EmptyEmailList() throws Exception {
        Long projectId = 1L;

        mockMvc.perform(post("/projects/{projectId}/members", projectId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/" + projectId + "/members"));

        verifyNoInteractions(userService, projectMemberService);
    }

    @Test
    public void testHandleAddProjectMember_NoUserFound() throws Exception {
        Long projectId = 1L;
        List<String> memberEmails = Arrays.asList("nonexistent@example.com");

        when(userService.getUserIdsByEmails(memberEmails)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 이메일을 찾을 수 없습니다."));

        mockMvc.perform(post("/projects/{projectId}/members", projectId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("memberEmails", memberEmails.toArray(new String[0])))
                .andExpect(status().isNotFound());

        verify(userService).getUserIdsByEmails(memberEmails);
        verifyNoMoreInteractions(projectMemberService);
        verifyNoMoreInteractions(userService);
    }
}
