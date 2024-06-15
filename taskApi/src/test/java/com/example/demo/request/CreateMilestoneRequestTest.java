package com.example.demo.request;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateMilestoneRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validCreateMilestoneRequest() {
        CreateMilestoneRequest request = new CreateMilestoneRequest(
                "Milestone 1",
                ZonedDateTime.now(),
                ZonedDateTime.now().plusDays(1)
        );

        Set<ConstraintViolation<CreateMilestoneRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void invalidCreateMilestoneRequest_nullName() {
        CreateMilestoneRequest request = new CreateMilestoneRequest(
                null,
                ZonedDateTime.now(),
                ZonedDateTime.now().plusDays(1)
        );

        Set<ConstraintViolation<CreateMilestoneRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());

        ConstraintViolation<CreateMilestoneRequest> violation = violations.iterator().next();
        assertEquals("널이어서는 안됩니다", violation.getMessage());
        assertEquals("milestoneName", violation.getPropertyPath().toString());
    }

    @Test
    void invalidCreateMilestoneRequest_nullStartDate() {
        CreateMilestoneRequest request = new CreateMilestoneRequest(
                "Milestone 1",
                null,
                ZonedDateTime.now().plusDays(1)
        );

        Set<ConstraintViolation<CreateMilestoneRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());

        ConstraintViolation<CreateMilestoneRequest> violation = violations.iterator().next();
        assertEquals("널이어서는 안됩니다", violation.getMessage());
        assertEquals("milestoneStartDate", violation.getPropertyPath().toString());
    }

    @Test
    void invalidCreateMilestoneRequest_nullEndDate() {
        CreateMilestoneRequest request = new CreateMilestoneRequest(
                "Milestone 1",
                ZonedDateTime.now(),
                null
        );

        Set<ConstraintViolation<CreateMilestoneRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());

        ConstraintViolation<CreateMilestoneRequest> violation = violations.iterator().next();
        assertEquals("널이어서는 안됩니다", violation.getMessage());
        assertEquals("milestoneEndDate", violation.getPropertyPath().toString());
    }
}
