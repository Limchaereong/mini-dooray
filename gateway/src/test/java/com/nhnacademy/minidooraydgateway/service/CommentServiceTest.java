package com.nhnacademy.minidooraydgateway.service;

import com.nhnacademy.minidooraydgateway.client.ProjectServiceClient;
import com.nhnacademy.minidooraydgateway.dto.CommentGetDto;
import com.nhnacademy.minidooraydgateway.dto.CreateCommentRequest;
import com.nhnacademy.minidooraydgateway.dto.UpdateCommentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CommentServiceTest {

    @Mock
    private ProjectServiceClient projectServiceClient;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateComment() {
        Long projectId = 1L;
        Long taskId = 1L;
        CreateCommentRequest request = new CreateCommentRequest("Test comment", taskId);

        doReturn(null).when(projectServiceClient).createComment(any(Long.class), any(Long.class), any(CreateCommentRequest.class));

        commentService.createComment(projectId, taskId, request);

        verify(projectServiceClient, times(1)).createComment(projectId, taskId, request);
    }

    @Test
    public void testUpdateComment() {
        Long projectId = 1L;
        Long taskId = 1L;
        Long commentId = 1L;
        UpdateCommentRequest request = new UpdateCommentRequest("Updated comment", taskId);

        doReturn(null).when(projectServiceClient).updateComment(any(UpdateCommentRequest.class), any(Long.class), any(Long.class), any(Long.class));

        commentService.updateComment(request, projectId, taskId, commentId);

        verify(projectServiceClient, times(1)).updateComment(request, projectId, taskId, commentId);
    }

    @Test
    public void testDeleteComment() {
        Long projectId = 1L;
        Long taskId = 1L;
        Long commentId = 1L;

        doReturn(null).when(projectServiceClient).deleteComment(any(Long.class), any(Long.class), any(Long.class));

        commentService.deleteComment(projectId, taskId, commentId);

        verify(projectServiceClient, times(1)).deleteComment(projectId, taskId, commentId);
    }

    @Test
    public void testGetTaskComments() {
        Long projectId = 1L;
        Long taskId = 1L;
        List<CommentGetDto> expectedComments = List.of(new CommentGetDto(1L, "Comment 1"), new CommentGetDto(2L, "Comment 2"));

        when(projectServiceClient.getTaskComments(any(Long.class), any(Long.class))).thenReturn(ResponseEntity.ok(expectedComments));

        List<CommentGetDto> actualComments = commentService.getTaskComments(projectId, taskId);

        assertEquals(expectedComments, actualComments);
        verify(projectServiceClient, times(1)).getTaskComments(projectId, taskId);
    }
}
