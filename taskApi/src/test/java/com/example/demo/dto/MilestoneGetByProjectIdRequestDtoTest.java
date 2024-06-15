package com.example.demo.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MilestoneGetByProjectIdRequestDtoTest {

    @Test
    void testMilestoneGetByProjectIdRequestDtoBuilder() {
        long projectId = 1L;
        MilestoneGetByProjectIdRequestDto dto = MilestoneGetByProjectIdRequestDto.builder()
                .projectId(projectId)
                .build();

        assertNotNull(dto);
        assertEquals(projectId, dto.projectId());
    }

    @Test
    void testMilestoneGetByProjectIdRequestDtoGetters() {
        long projectId = 2L;
        MilestoneGetByProjectIdRequestDto dto = new MilestoneGetByProjectIdRequestDto(projectId);

        assertNotNull(dto);
        assertEquals(projectId, dto.projectId());
    }
}
