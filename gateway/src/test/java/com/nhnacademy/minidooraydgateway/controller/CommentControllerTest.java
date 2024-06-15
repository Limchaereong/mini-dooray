package com.nhnacademy.minidooraydgateway.controller;

import com.nhnacademy.minidooraydgateway.dto.CommentGetDto;
import com.nhnacademy.minidooraydgateway.dto.CreateCommentRequest;
import com.nhnacademy.minidooraydgateway.dto.UpdateCommentRequest;
import com.nhnacademy.minidooraydgateway.service.CommentService;
import com.nhnacademy.minidooraydgateway.util.SecurityContextUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @Mock
    private SecurityContextUtil securityContextUtil;

    @InjectMocks
    private CommentController commentController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    public void testGetNewCommentPage() throws Exception {
        List<CommentGetDto> comments = Arrays.asList(
                CommentGetDto.builder().commentId(1L).comment("First comment").build(),
                CommentGetDto.builder().commentId(2L).comment("Second comment").build()
        );

        when(commentService.getTaskComments(1L, 1L)).thenReturn(comments);

        mockMvc.perform(get("/projects/1/tasks/1/comments"))
                .andExpect(status().isOk())
                .andExpect(view().name("project/comments"))
                .andExpect(model().attributeExists("comments"))
                .andExpect(model().attributeExists("projectId"))
                .andExpect(model().attributeExists("taskId"));

        verify(commentService, times(1)).getTaskComments(1L, 1L);
    }

    @Test
    public void testHandleCreateComment() throws Exception {
        mockMvc.perform(post("/projects/1/tasks/1/comments")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("comment", "New Comment"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/1/tasks/1/comments"));

        verify(commentService, times(1)).createComment(eq(1L), eq(1L), any(CreateCommentRequest.class));
    }

    @Test
    public void testHandleEditComment() throws Exception {
        mockMvc.perform(post("/projects/1/tasks/1/comments/1/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("update_comment", "Updated Comment"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/1/tasks/1/comments"));

        verify(commentService, times(1)).updateComment(any(UpdateCommentRequest.class), eq(1L), eq(1L), eq(1L));
    }

    @Test
    public void testHandleDeleteComment() throws Exception {
        mockMvc.perform(post("/projects/1/tasks/1/comments/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects/1/tasks/1/comments"));

        verify(commentService, times(1)).deleteComment(1L, 1L, 1L);
    }
}
