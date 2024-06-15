package com.example.demo.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTagTest {

    private Project project;
    private Milestone milestone;
    private Task task;
    private Tag tag;

    @BeforeEach
    void setUp() {
        project = new Project(1L, "Project Name", Project.ProjectStatus.ACTIVE);
        milestone = new Milestone(1L, "Milestone Name", ZonedDateTime.now(), ZonedDateTime.now().plusDays(1), project);
        task = new Task("Task Name", "Task Description", Task.TaskStatus.TODO, project, milestone);
        tag = new Tag("Tag Name", project);
    }

    @Test
    void testTaskTagCreation() {
        // Given
        TaskTagPk taskTagPk = new TaskTagPk(1L, 1L);

        // When
        TaskTag taskTag = new TaskTag(taskTagPk, task, tag);

        // Then
        assertNotNull(taskTag);
        assertEquals(taskTagPk, taskTag.getTaskTagPk());
        assertEquals(task, taskTag.getTask());
        assertEquals(tag, taskTag.getTag());
    }

    @Test
    void testTaskTagPkEqualsAndHashCode() {
        // Given
        TaskTagPk pk1 = new TaskTagPk(1L, 1L);
        TaskTagPk pk2 = new TaskTagPk(1L, 1L);
        TaskTagPk pk3 = new TaskTagPk(2L, 2L);

        // When & Then
        assertEquals(pk1, pk2);
        assertNotEquals(pk1, pk3);
        assertEquals(pk1.hashCode(), pk2.hashCode());
        assertNotEquals(pk1.hashCode(), pk3.hashCode());
    }
}
