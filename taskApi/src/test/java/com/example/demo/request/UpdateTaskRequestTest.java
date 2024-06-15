package com.example.demo.request;

import com.example.demo.entity.Task;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UpdateTaskRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testConstructorAndGetters_Valid() {
        UpdateTaskRequest request = new UpdateTaskRequest("Task Name", "Task Description", Task.TaskStatus.TODO, 1L);

        assertEquals("Task Name", request.taskName());
        assertEquals("Task Description", request.taskDescription());
        assertEquals(Task.TaskStatus.TODO, request.taskStatus());
        assertEquals(1L, request.milestoneId());
    }

    @Test
    void testValidation_Valid() {
        UpdateTaskRequest request = new UpdateTaskRequest("Valid Task Name", "Task Description", Task.TaskStatus.TODO, 1L);

        Set<ConstraintViolation<UpdateTaskRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidation_Invalid_NullTaskName() {
        UpdateTaskRequest request = new UpdateTaskRequest(null, "Task Description", Task.TaskStatus.TODO, 1L);

        Set<ConstraintViolation<UpdateTaskRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<UpdateTaskRequest> violation = violations.iterator().next();
        assertEquals("taskName", violation.getPropertyPath().toString());
        assertEquals("널이어서는 안됩니다", violation.getMessage());
    }

    @Test
    void testValidation_NullTaskStatus() {
        UpdateTaskRequest request = new UpdateTaskRequest("Task Name", "Task Description", null, 1L);

        Set<ConstraintViolation<UpdateTaskRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidation_NullMilestoneId() {
        UpdateTaskRequest request = new UpdateTaskRequest("Task Name", "Task Description", Task.TaskStatus.TODO, null);

        Set<ConstraintViolation<UpdateTaskRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }
}
