package com.example.demo.controller;

import com.example.demo.dto.TaskCreateDto;
import com.example.demo.dto.TaskGetDto;
import com.example.demo.request.TaskUpdateRequest;
import com.example.demo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTasks_shouldReturnListOfTasks() {
        // given
        List<TaskGetDto> taskList = List.of(
                new TaskGetDto(1L, "Task1", "Description1", null, 1L, 1L),
                new TaskGetDto(2L, "Task2", "Description2", null, 1L, 2L)
        );
        when(taskService.findAllTask(anyLong())).thenReturn(taskList);

        // when
        ResponseEntity<List<TaskGetDto>> response = taskController.getTasks(1L);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskList, response.getBody());
    }

    @Test
    void getTask_shouldReturnTask() {
        // given
        TaskGetDto task = new TaskGetDto(1L, "Task1", "Description1", null, 1L, 1L);
        when(taskService.getById(anyLong())).thenReturn(task);

        // when
        ResponseEntity<TaskGetDto> response = taskController.getTask(1L);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    @Test
    void createTask_shouldCreateTask() {
        // given
        TaskCreateDto request = TaskCreateDto.builder()
                .name("Task1")
                .description("Description1")
                .status(null)
                .milestoneId(1L)
                .build();
        when(taskService.create(any(TaskCreateDto.class), anyLong())).thenReturn(request);

        // when
        ResponseEntity<TaskCreateDto> response = taskController.createTask(request, 1L);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(request, response.getBody());
    }

    @Test
    void updateTask_shouldUpdateTask() {
        // given
        TaskUpdateRequest request = new TaskUpdateRequest("UpdatedTask", "UpdatedDescription", null, 1L);
        doNothing().when(taskService).modifyById(any(TaskUpdateRequest.class), anyLong());

        // when
        taskController.updateTask(request, 1L);

        // then
        verify(taskService, times(1)).modifyById(any(TaskUpdateRequest.class), anyLong());
    }

    @Test
    void deleteTask_shouldDeleteTask() {
        // given
        doNothing().when(taskService).deleteById(anyLong());

        // when
        taskController.deleteTask(1L, 1L);

        // then
        verify(taskService, times(1)).deleteById(anyLong());
    }
}
