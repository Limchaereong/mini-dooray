package com.example.demo.request;

import com.example.demo.entity.Project;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectDtoTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testSerialization() throws JsonProcessingException {
        ProjectDto projectDto = new ProjectDto("Test Project", Project.ProjectStatus.ACTIVE, 1L, List.of(2L, 3L));
        String json = objectMapper.writeValueAsString(projectDto);

        assertNotNull(json);
        assertTrue(json.contains("\"name\":\"Test Project\""));
        assertTrue(json.contains("\"status\":\"ACTIVE\""));
        assertTrue(json.contains("\"adminUserId\":1"));
        assertTrue(json.contains("\"membersId\":[2,3]"));
    }

    @Test
    void testDeserialization() throws JsonProcessingException {
        String json = "{\"name\":\"Test Project\",\"status\":\"ACTIVE\",\"adminUserId\":1,\"membersId\":[2,3]}";
        ProjectDto projectDto = objectMapper.readValue(json, ProjectDto.class);

        assertNotNull(projectDto);
        assertEquals("Test Project", projectDto.name());
        assertEquals(Project.ProjectStatus.ACTIVE, projectDto.status());
        assertEquals(1L, projectDto.adminUserId());
        assertEquals(List.of(2L, 3L), projectDto.membersId());
    }

    @Test
    void testGetters() {
        ProjectDto projectDto = new ProjectDto("Test Project", Project.ProjectStatus.ACTIVE, 1L, List.of(2L, 3L));

        assertEquals("Test Project", projectDto.name());
        assertEquals(Project.ProjectStatus.ACTIVE, projectDto.status());
        assertEquals(1L, projectDto.adminUserId());
        assertEquals(List.of(2L, 3L), projectDto.membersId());
    }
}
