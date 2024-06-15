package com.example.demo.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MilestoneGetByMilestoneIdRequestDtoTest {

    @Test
    void testMilestoneGetByMilestoneIdRequestDtoBuilder() {
        Long milestoneId = 1L;
        MilestoneGetByMilestoneIdRequestDto dto = MilestoneGetByMilestoneIdRequestDto.builder()
                .milestoneId(milestoneId)
                .build();

        assertNotNull(dto);
        assertEquals(milestoneId, dto.milestoneId());
    }

    @Test
    void testMilestoneGetByMilestoneIdRequestDtoGetters() {
        Long milestoneId = 2L;
        MilestoneGetByMilestoneIdRequestDto dto = new MilestoneGetByMilestoneIdRequestDto(milestoneId);

        assertNotNull(dto);
        assertEquals(milestoneId, dto.milestoneId());
    }
}
