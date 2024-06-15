package com.example.demo.dto;

import com.example.demo.entity.Milestone;
import com.example.demo.entity.Project;
import com.example.demo.entity.Task;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaskReadResponseDtoTest {

    @Test
    void testTaskReadResponseDtoBuilder() {
        Project project = new Project(1L, "Project Name", Project.ProjectStatus.ACTIVE);
        Milestone milestone = new Milestone(1L, "Milestone Name", ZonedDateTime.now(), ZonedDateTime.now().plusDays(1), project);
        Task task = Task.builder()
                .taskName("Task Name")
                .taskDescription("Task Description")
                .taskStatus(Task.TaskStatus.TODO)
                .project(project)
                .milestone(milestone)
                .build();

        TaskReadResponseDto dto = TaskReadResponseDto.builder()
                .task(task)
                .build();

        assertNotNull(dto);
        assertEquals(task, dto.task());
    }

    @Test
    void testTaskReadResponseDtoGetters() {
        Project project = new Project(1L, "Project Name", Project.ProjectStatus.ACTIVE);
        Milestone milestone = new Milestone(1L, "Milestone Name", ZonedDateTime.now(), ZonedDateTime.now().plusDays(1), project);
        Task task = Task.builder()
                .taskName("Task Name")
                .taskDescription("Task Description")
                .taskStatus(Task.TaskStatus.TODO)
                .project(project)
                .milestone(milestone)
                .build();

        TaskReadResponseDto dto = new TaskReadResponseDto(task);

        assertNotNull(dto);
        assertEquals(task, dto.task());
    }
}
