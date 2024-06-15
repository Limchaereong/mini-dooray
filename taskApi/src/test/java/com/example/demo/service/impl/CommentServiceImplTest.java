package com.example.demo.service.impl;

import com.example.demo.dto.CommentGetDto;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Task;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.request.CreateCommentRequest;
import com.example.demo.request.UpdateCommentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Comment comment;
    private Task task;

    @BeforeEach
    public void setUp() throws Exception {
        task = createTask(1L);

        comment = createComment(1L, "Test Comment", task);
    }

    private Task createTask(Long taskId) throws Exception {
        Constructor<Task> constructor = Task.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Task task = constructor.newInstance();
        setField(task, "taskId", taskId);
        return task;
    }

    private Comment createComment(Long commentId, String commentContent, Task task) throws Exception {
        Constructor<Comment> constructor = Comment.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Comment comment = constructor.newInstance();
        setField(comment, "commentId", commentId);
        setField(comment, "commentContent", commentContent);
        setField(comment, "task", task);
        return comment;
    }

    private void setField(Object object, String fieldName, Object value) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    @Test
    public void testGetCommentsByTaskId() {
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);

        when(commentRepository.findAllByTaskTaskId(1L)).thenReturn(comments);

        List<CommentGetDto> result = commentService.getCommentsByTaskId(1L);

        assertEquals(1, result.size());
        assertEquals("Test Comment", result.get(0).comment());
        verify(commentRepository, times(1)).findAllByTaskTaskId(1L);
    }

    @Test
    public void testGetCommentById() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        Comment result = commentService.getCommentById(1L);

        assertNotNull(result);
        assertEquals("Test Comment", result.getCommentContent());
        verify(commentRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateComment() {
        CreateCommentRequest createCommentRequest = new CreateCommentRequest("New Comment", 1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        commentService.createComment(createCommentRequest, 1L);

        ArgumentCaptor<Comment> commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository, times(1)).save(commentArgumentCaptor.capture());
        assertEquals("New Comment", commentArgumentCaptor.getValue().getCommentContent());
        assertEquals(task, commentArgumentCaptor.getValue().getTask());
    }

    @Test
    public void testCreateComment_TaskNotFound() {
        CreateCommentRequest createCommentRequest = new CreateCommentRequest("New Comment", 1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.createComment(createCommentRequest, 1L);
        });

        assertEquals("Invalid Task ID", exception.getMessage());
    }


    @Test
    public void testUpdateComment() {
        UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest("Updated Comment", 1L);

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        commentService.updateComment(1L, updateCommentRequest);

        ArgumentCaptor<Comment> commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository, times(1)).save(commentArgumentCaptor.capture());
        assertEquals("Updated Comment", commentArgumentCaptor.getValue().getCommentContent());
    }

    @Test
    public void testUpdateComment_CommentNotFound() {
        UpdateCommentRequest updateCommentRequest = new UpdateCommentRequest("Updated Comment", 1L);

        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            commentService.updateComment(1L, updateCommentRequest);
        });

        assertEquals("Invalid Comment ID", exception.getMessage());
    }

    @Test
    public void testDeleteComment() {
        when(commentRepository.existsById(1L)).thenReturn(true);

        commentService.deleteComment(1L);

        verify(commentRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteComment_CommentNotFound() {
        when(commentRepository.existsById(1L)).thenReturn(false);

        commentService.deleteComment(1L);

        verify(commentRepository, times(0)).deleteById(1L);
    }
}
