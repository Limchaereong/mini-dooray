package com.nhnacademy.minidooraydgateway.service;

import com.nhnacademy.minidooraydgateway.client.ProjectServiceClient;
import com.nhnacademy.minidooraydgateway.domain.Task;
import com.nhnacademy.minidooraydgateway.dto.TaskCreateDto;
import com.nhnacademy.minidooraydgateway.dto.TaskGetDto;
import com.nhnacademy.minidooraydgateway.dto.TaskUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private ProjectServiceClient projectServiceClient;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTaskByTaskId_shouldReturnTask() {
        // given
        TaskGetDto task = TaskGetDto.builder()
                .id(1L)
                .name("TaskName")
                .description("Description")
                .status(Task.TaskStatus.TODO)
                .projectId(1L)
                .milestoneId(1L)
                .build();
        when(projectServiceClient.getTaskByTaskId(anyLong(), anyLong())).thenReturn(new ResponseEntity<>(task, HttpStatus.OK));

        // when
        TaskGetDto result = taskService.getTaskByTaskId(1L, 1L);

        // then
        assertNotNull(result);
        assertEquals("TaskName", result.name());
        verify(projectServiceClient, times(1)).getTaskByTaskId(anyLong(), anyLong());
    }

    @Test
    void getAllTasksByProjectId_shouldReturnListOfTasks() {
        // given
        TaskGetDto task = TaskGetDto.builder()
                .id(1L)
                .name("TaskName")
                .description("Description")
                .status(Task.TaskStatus.TODO)
                .projectId(1L)
                .milestoneId(1L)
                .build();
        when(projectServiceClient.getAllTasks(anyLong())).thenReturn(new ResponseEntity<>(Collections.singletonList(task), HttpStatus.OK));

        // when
        List<TaskGetDto> result = taskService.getAllTasksByProjectId(1L);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(projectServiceClient, times(1)).getAllTasks(anyLong());
    }
//
//    @Test
//    void createTask_shouldCallCreateTaskOnProjectServiceClient() {
//        // given
//        TaskCreateDto taskCreateDto = TaskCreateDto.builder()
//                .name("TaskName")
//                .description("Description")
//                .status(Task.TaskStatus.TODO)
//                .milestoneId(1L)
//                .build();
//
//        doNothing().when(projectServiceClient).createTask(anyLong(), any(TaskCreateDto.class));
//
//        // when
//        taskService.createTask(1L, taskCreateDto);
//
//        // then
//        verify(projectServiceClient, times(1)).createTask(anyLong(), any(TaskCreateDto.class));
//    }
//
//    @Test
//    void updateTask_shouldCallUpdateTaskOnProjectServiceClient() {
//        // given
//        TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest("UpdatedTaskName", "UpdatedDescription", Task.TaskStatus.IN_PROGRESS, 1L);
//
//        doNothing().when(projectServiceClient).updateTask(anyLong(), anyLong(), any(TaskUpdateRequest.class));
//
//        // when
//        taskService.updateTask(1L, 1L, taskUpdateRequest);
//
//        // then
//        verify(projectServiceClient, times(1)).updateTask(anyLong(), anyLong(), any(TaskUpdateRequest.class));
//    }
//
//    @Test
//    void deleteTask_shouldCallDeleteTaskOnProjectServiceClient() {
//        // given
//        doNothing().when(projectServiceClient).deleteTask(anyLong(), anyLong());
//
//        // when
//        taskService.deleteTask(1L, 1L);
//
//        // then
//        verify(projectServiceClient, times(1)).deleteTask(anyLong(), anyLong());
//    }

    @Test
    void setTaskTag_shouldCallSetTaskTagOnProjectServiceClient() {
        // given
        doNothing().when(projectServiceClient).setTaskTag(anyLong(), anyLong(), anyLong());

        // when
        taskService.setTaskTag(1L, 1L, 1L);

        // then
        verify(projectServiceClient, times(1)).setTaskTag(anyLong(), anyLong(), anyLong());
    }

    @Test
    void deleteTaskTag_shouldCallDeleteTaskTagOnProjectServiceClient() {
        // given
        doNothing().when(projectServiceClient).deleteTaskTag(anyLong(), anyLong());

        // when
        taskService.deleteTaskTag(1L, 1L);

        // then
        verify(projectServiceClient, times(1)).deleteTaskTag(anyLong(), anyLong());
    }

    @Test
    void setTaskMilestone_shouldCallSetTaskMilestoneOnProjectServiceClient() {
        // given
        doNothing().when(projectServiceClient).setTaskMilestone(anyLong(), anyLong(), anyLong());

        // when
        taskService.setTaskMilestone(1L, 1L, 1L);

        // then
        verify(projectServiceClient, times(1)).setTaskMilestone(anyLong(), anyLong(), anyLong());
    }

    @Test
    void deleteTaskMilestone_shouldCallDeleteTaskMilestoneOnProjectServiceClient() {
        // given
        doNothing().when(projectServiceClient).deleteTaskMilestone(anyLong(), anyLong());

        // when
        taskService.deleteTaskMilestone(1L, 1L);

        // then
        verify(projectServiceClient, times(1)).deleteTaskMilestone(anyLong(), anyLong());
    }
}
