package com.example.demo.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UpdateTagRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testConstructorAndGetters_Valid() {
        UpdateTagRequest request = new UpdateTagRequest("Test Tag", 1L);

        assertEquals("Test Tag", request.tagName());
        assertEquals(1L, request.projectId());
    }

    @Test
    void testValidation_Valid() {
        UpdateTagRequest request = new UpdateTagRequest("Valid Tag", 1L);

        Set<ConstraintViolation<UpdateTagRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidation_Invalid_NullTagName() {
        UpdateTagRequest request = new UpdateTagRequest(null, 1L);

        Set<ConstraintViolation<UpdateTagRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<UpdateTagRequest> violation = violations.iterator().next();
        assertEquals("tagName", violation.getPropertyPath().toString());
        assertEquals("널이어서는 안됩니다", violation.getMessage());
    }

    @Test
    void testValidation_NullProjectId() {
        UpdateTagRequest request = new UpdateTagRequest("Valid Tag", null);

        Set<ConstraintViolation<UpdateTagRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }
}
