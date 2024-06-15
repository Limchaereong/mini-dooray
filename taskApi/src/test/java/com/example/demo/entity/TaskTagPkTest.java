package com.example.demo.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTagPkTest {

    @Test
    void testNoArgsConstructor() {
        // Given
        TaskTagPk taskTagPk = new TaskTagPk();

        // Then
        assertNotNull(taskTagPk);
        assertNull(taskTagPk.getTaskId());
        assertNull(taskTagPk.getTagId());
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Long taskId = 1L;
        Long tagId = 2L;

        // When
        TaskTagPk taskTagPk = new TaskTagPk(taskId, tagId);

        // Then
        assertNotNull(taskTagPk);
        assertEquals(taskId, taskTagPk.getTaskId());
        assertEquals(tagId, taskTagPk.getTagId());
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        TaskTagPk pk1 = new TaskTagPk(1L, 2L);
        TaskTagPk pk2 = new TaskTagPk(1L, 2L);
        TaskTagPk pk3 = new TaskTagPk(2L, 3L);

        // When & Then
        assertEquals(pk1, pk2);
        assertNotEquals(pk1, pk3);
        assertEquals(pk1.hashCode(), pk2.hashCode());
        assertNotEquals(pk1.hashCode(), pk3.hashCode());
    }

    @Test
    void testGetterMethods() {
        // Given
        Long taskId = 1L;
        Long tagId = 2L;

        // When
        TaskTagPk taskTagPk = new TaskTagPk(taskId, tagId);

        // Then
        assertEquals(taskId, taskTagPk.getTaskId());
        assertEquals(tagId, taskTagPk.getTagId());
    }
}
