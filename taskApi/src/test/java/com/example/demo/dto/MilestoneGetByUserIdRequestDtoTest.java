package com.example.demo.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MilestoneGetByUserIdRequestDtoTest {

    @Test
    void testMilestoneGetByUserIdRequestDtoBuilder() {
        long userId = 1L;
        MilestoneGetByUserIdRequestDto dto = MilestoneGetByUserIdRequestDto.builder()
                .userId(userId)
                .build();

        assertNotNull(dto);
        assertEquals(userId, dto.userId());
    }

    @Test
    void testMilestoneGetByUserIdRequestDtoGetters() {
        long userId = 2L;
        MilestoneGetByUserIdRequestDto dto = new MilestoneGetByUserIdRequestDto(userId);

        assertNotNull(dto);
        assertEquals(userId, dto.userId());
    }
}
