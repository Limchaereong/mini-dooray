package com.example.demo.request;

import com.example.demo.entity.Task;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CreateTaskRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validCreateTaskRequest() {
        CreateTaskRequest request = new CreateTaskRequest(
                "Task Name",
                "Task Description",
                Task.TaskStatus.TODO,
                1L
        );

        Set<ConstraintViolation<CreateTaskRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidCreateTaskRequest_nullName() {
        CreateTaskRequest request = new CreateTaskRequest(
                null,
                "Task Description",
                Task.TaskStatus.TODO,
                1L
        );

        Set<ConstraintViolation<CreateTaskRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("널이어서는 안됩니다", violations.iterator().next().getMessage());
    }

    @Test
    void validCreateTaskRequest_nullDescription() {
        CreateTaskRequest request = new CreateTaskRequest(
                "Task Name",
                null,
                Task.TaskStatus.TODO,
                1L
        );

        Set<ConstraintViolation<CreateTaskRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void validCreateTaskRequest_nullTaskStatus() {
        CreateTaskRequest request = new CreateTaskRequest(
                "Task Name",
                "Task Description",
                null,
                1L
        );

        Set<ConstraintViolation<CreateTaskRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void validCreateTaskRequest_nullMilestoneId() {
        CreateTaskRequest request = new CreateTaskRequest(
                "Task Name",
                "Task Description",
                Task.TaskStatus.TODO,
                null
        );

        Set<ConstraintViolation<CreateTaskRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }
}
