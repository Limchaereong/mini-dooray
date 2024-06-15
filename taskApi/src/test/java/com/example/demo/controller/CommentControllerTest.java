package com.example.demo.controller;

import com.example.demo.dto.CommentGetDto;
import com.example.demo.request.CreateCommentRequest;
import com.example.demo.request.UpdateCommentRequest;
import com.example.demo.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getComments() {
        // Arrange
        Long taskId = 1L;
        List<CommentGetDto> commentsList = Arrays.asList(
                new CommentGetDto(1L, "user1", "Comment 1"),
                new CommentGetDto(2L, "user2", "Comment 2")
        );
        when(commentService.getCommentsByTaskId(taskId)).thenReturn(commentsList);

        // Act
        ResponseEntity<List<CommentGetDto>> response = commentController.getComments(1L, taskId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(commentsList);
        verify(commentService, times(1)).getCommentsByTaskId(taskId);
    }

    @Test
    void createComment() {
        // Arrange
        Long taskId = 1L;
        CreateCommentRequest request = CreateCommentRequest.builder()
                .commentContent("New Comment")
                .taskId(taskId)
                .build();

        // Act
        ResponseEntity<Void> response = commentController.createComment(request, 1L, taskId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(commentService, times(1)).createComment(eq(request), eq(taskId));
    }

    @Test
    void updateComment() {
        // Arrange
        Long commentId = 1L;
        Long taskId = 1L;
        UpdateCommentRequest request = new UpdateCommentRequest("Updated Comment", taskId);

        // Act
        ResponseEntity<Void> response = commentController.updateComment(request, 1L, taskId, commentId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(commentService, times(1)).updateComment(eq(commentId), eq(request));
    }

    @Test
    void deleteComment() {
        // Arrange
        Long commentId = 1L;
        Long taskId = 1L;

        // Act
        ResponseEntity<Void> response = commentController.deleteComment(1L, taskId, commentId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(commentService, times(1)).deleteComment(eq(commentId));
    }
}
