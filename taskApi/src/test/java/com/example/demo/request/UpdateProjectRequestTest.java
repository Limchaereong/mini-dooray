package com.example.demo.request;

import com.example.demo.entity.Project;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UpdateProjectRequestTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testSerialization() throws JsonProcessingException {
        UpdateProjectRequest request = new UpdateProjectRequest("Test Project", Project.ProjectStatus.ACTIVE);
        String json = objectMapper.writeValueAsString(request);

        assertNotNull(json);
        assertTrue(json.contains("\"projectName\":\"Test Project\""));
        assertTrue(json.contains("\"projectStatus\":\"ACTIVE\""));
    }

    @Test
    void testDeserialization() throws JsonProcessingException {
        String json = "{\"projectName\":\"Test Project\",\"projectStatus\":\"ACTIVE\"}";
        UpdateProjectRequest request = objectMapper.readValue(json, UpdateProjectRequest.class);

        assertNotNull(request);
        assertEquals("Test Project", request.projectName());
        assertEquals(Project.ProjectStatus.ACTIVE, request.projectStatus());
    }

    @Test
    void testValidation_Valid() {
        UpdateProjectRequest request = new UpdateProjectRequest("Valid Project", Project.ProjectStatus.DORMANT);
        Set<ConstraintViolation<UpdateProjectRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidation_Invalid_NullName() {
        UpdateProjectRequest request = new UpdateProjectRequest(null, Project.ProjectStatus.ENDED);
        Set<ConstraintViolation<UpdateProjectRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("널이어서는 안됩니다", violations.iterator().next().getMessage());
    }

    @Test
    void testValidation_Invalid_NullStatus() {
        UpdateProjectRequest request = new UpdateProjectRequest("Invalid Project", null);
        Set<ConstraintViolation<UpdateProjectRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("널이어서는 안됩니다", violations.iterator().next().getMessage());
    }

    @Test
    void testGetters() {
        UpdateProjectRequest request = new UpdateProjectRequest("Test Project", Project.ProjectStatus.ACTIVE);

        assertEquals("Test Project", request.projectName());
        assertEquals(Project.ProjectStatus.ACTIVE, request.projectStatus());
    }
}
