package com.example.demo.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProjectGetByUserIdRequestDtoTest {

    @Test
    void testProjectGetByUserIdRequestDtoBuilder() {
        long userId = 1L;

        ProjectGetByUserIdRequestDto dto = ProjectGetByUserIdRequestDto.builder()
                .userId(userId)
                .build();

        assertNotNull(dto);
        assertEquals(userId, dto.userId());
    }

    @Test
    void testProjectGetByUserIdRequestDtoGetters() {
        long userId = 1L;

        ProjectGetByUserIdRequestDto dto = new ProjectGetByUserIdRequestDto(userId);

        assertNotNull(dto);
        assertEquals(userId, dto.userId());
    }
}
