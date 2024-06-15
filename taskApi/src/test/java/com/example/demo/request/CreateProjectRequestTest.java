package com.example.demo.request;

import com.example.demo.entity.Project;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateProjectRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validCreateProjectRequest() {
        CreateProjectRequest request = new CreateProjectRequest(
                "Project 1",
                Project.ProjectStatus.ACTIVE
        );

        Set<ConstraintViolation<CreateProjectRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidCreateProjectRequest_nullName() {
        CreateProjectRequest request = new CreateProjectRequest(
                null,
                Project.ProjectStatus.ACTIVE
        );

        Set<ConstraintViolation<CreateProjectRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("널이어서는 안됩니다", violations.iterator().next().getMessage());
    }

    @Test
    void invalidCreateProjectRequest_nullStatus() {
        CreateProjectRequest request = new CreateProjectRequest(
                "Project 1",
                null
        );

        Set<ConstraintViolation<CreateProjectRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("널이어서는 안됩니다", violations.iterator().next().getMessage());
    }
}
