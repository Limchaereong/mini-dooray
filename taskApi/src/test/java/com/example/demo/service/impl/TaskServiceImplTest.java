package com.example.demo.service.impl;

import com.example.demo.dto.TaskCreateDto;
import com.example.demo.dto.TaskGetDto;
import com.example.demo.entity.Milestone;
import com.example.demo.entity.Project;
import com.example.demo.entity.Task;
import com.example.demo.error.ResourceNotFoundException;
import com.example.demo.repository.MilestoneRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.request.TaskUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private MilestoneRepository milestoneRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllTask_shouldReturnListOfTasks() {
        // given
        List<Task> tasks = new ArrayList<>();
        Project project = Project.builder().projectId(1L).projectName("Project1").projectStatus(Project.ProjectStatus.ACTIVE).build();
        Milestone milestone = Milestone.builder().milestoneId(1L).milestoneName("Milestone1").project(project).build();
        Task task1 = Task.builder().taskId(1L).taskName("Task1").taskDescription("Description1").taskStatus(Task.TaskStatus.TODO).project(project).milestone(milestone).build();
        Task task2 = Task.builder().taskId(2L).taskName("Task2").taskDescription("Description2").taskStatus(Task.TaskStatus.IN_PROGRESS).project(project).milestone(milestone).build();
        tasks.add(task1);
        tasks.add(task2);
        when(taskRepository.findAllByProjectProjectId(anyLong())).thenReturn(tasks);

        // when
        List<TaskGetDto> result = taskService.findAllTask(1L);

        // then
        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAllByProjectProjectId(anyLong());
    }

    @Test
    void getById_shouldReturnTask() {
        // given
        Project project = Project.builder().projectId(1L).projectName("Project1").projectStatus(Project.ProjectStatus.ACTIVE).build();
        Milestone milestone = Milestone.builder().milestoneId(1L).milestoneName("Milestone1").project(project).build();
        Task task = Task.builder().taskId(1L).taskName("Task1").taskDescription("Description1").taskStatus(Task.TaskStatus.TODO).project(project).milestone(milestone).build();
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));

        // when
        TaskGetDto result = taskService.getById(1L);

        // then
        assertNotNull(result);
        assertEquals("Task1", result.name());
        verify(taskRepository, times(1)).findById(anyLong());
    }

    @Test
    void getById_shouldThrowResourceNotFoundException() {
        // given
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        assertThrows(ResourceNotFoundException.class, () -> taskService.getById(1L));
        verify(taskRepository, times(1)).findById(anyLong());
    }

    @Test
    void create_shouldCreateTask() {
        // given
        TaskCreateDto request = TaskCreateDto.builder()
                .name("Task1")
                .description("Description1")
                .status(Task.TaskStatus.TODO)
                .milestoneId(1L)
                .build();
        Project project = Project.builder().projectId(1L).projectName("Project1").projectStatus(Project.ProjectStatus.ACTIVE).build();
        Milestone milestone = Milestone.builder().milestoneId(1L).milestoneName("Milestone1").project(project).build();
        when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
        when(milestoneRepository.findById(anyLong())).thenReturn(Optional.of(milestone));
        when(taskRepository.save(any(Task.class))).thenReturn(Task.builder().build());

        // when
        TaskCreateDto result = taskService.create(request, 1L);

        // then
        assertNotNull(result);
        assertEquals("Task1", result.name());
        verify(projectRepository, times(1)).findById(anyLong());
        verify(milestoneRepository, times(1)).findById(anyLong());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void create_shouldThrowResourceNotFoundException_whenProjectNotFound() {
        // given
        TaskCreateDto request = TaskCreateDto.builder()
                .name("Task1")
                .description("Description1")
                .status(Task.TaskStatus.TODO)
                .milestoneId(1L)
                .build();
        when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        assertThrows(ResourceNotFoundException.class, () -> taskService.create(request, 1L));
        verify(projectRepository, times(1)).findById(anyLong());
    }

    @Test
    void modifyById_shouldUpdateTask() {
        // given
        TaskUpdateRequest request = new TaskUpdateRequest("UpdatedTask", "UpdatedDescription", Task.TaskStatus.IN_PROGRESS, 1L);
        when(taskRepository.existsById(anyLong())).thenReturn(true);
        Milestone milestone = Milestone.builder().milestoneId(1L).milestoneName("Milestone1").build();
        when(milestoneRepository.findById(anyLong())).thenReturn(Optional.of(milestone));

        // when
        taskService.modifyById(request, 1L);

        // then
        verify(taskRepository, times(1)).existsById(anyLong());
        verify(taskRepository, times(1)).updateByTaskId(anyLong(), anyString(), anyString(), any(Task.TaskStatus.class), any(Milestone.class));
    }

    @Test
    void modifyById_shouldThrowIllegalArgumentException_whenTaskNotFound() {
        // given
        TaskUpdateRequest request = new TaskUpdateRequest("UpdatedTask", "UpdatedDescription", Task.TaskStatus.IN_PROGRESS, 1L);
        when(taskRepository.existsById(anyLong())).thenReturn(false);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> taskService.modifyById(request, 1L));
        verify(taskRepository, times(1)).existsById(anyLong());
    }

    @Test
    void deleteById_shouldDeleteTask() {
        // given
        when(taskRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(taskRepository).deleteById(anyLong());

        // when
        taskService.deleteById(1L);

        // then
        verify(taskRepository, times(1)).existsById(anyLong());
        verify(taskRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteById_shouldThrowIllegalArgumentException_whenTaskNotFound() {
        // given
        when(taskRepository.existsById(anyLong())).thenReturn(false);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> taskService.deleteById(1L));
        verify(taskRepository, times(1)).existsById(anyLong());
    }
}
