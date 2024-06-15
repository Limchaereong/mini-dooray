package com.example.demo.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UpdateTagNameRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testGetterAndSetter() {
        UpdateTagNameRequest request = new UpdateTagNameRequest();
        request.setTagName("Test Tag");

        assertEquals("Test Tag", request.getTagName());
    }

    @Test
    void testValidation_Valid() {
        UpdateTagNameRequest request = new UpdateTagNameRequest();
        request.setTagName("Valid Tag");

        Set<ConstraintViolation<UpdateTagNameRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidation_Invalid_NullTagName() {
        UpdateTagNameRequest request = new UpdateTagNameRequest();
        request.setTagName(null);

        Set<ConstraintViolation<UpdateTagNameRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<UpdateTagNameRequest> violation = violations.iterator().next();
        assertEquals("Tag name cannot be null", violation.getMessage());
        assertEquals("tagName", violation.getPropertyPath().toString());
    }
}
