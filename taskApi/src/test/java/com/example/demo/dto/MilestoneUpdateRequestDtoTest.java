package com.example.demo.dto;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MilestoneUpdateRequestDtoTest {

    @Test
    void testMilestoneUpdateRequestDtoBuilder() {
        Long milestoneId = 1L;
        String milestoneName = "Test Milestone";
        ZonedDateTime milestoneStartDate = ZonedDateTime.now().minusDays(1);
        ZonedDateTime milestoneEndDate = ZonedDateTime.now().plusDays(1);

        MilestoneUpdateRequestDto dto = MilestoneUpdateRequestDto.builder()
                .milestoneId(milestoneId)
                .milestoneName(milestoneName)
                .milestoneStartDate(milestoneStartDate)
                .milestoneEndDate(milestoneEndDate)
                .build();

        assertNotNull(dto);
        assertEquals(milestoneId, dto.milestoneId());
        assertEquals(milestoneName, dto.milestoneName());
        assertEquals(milestoneStartDate, dto.milestoneStartDate());
        assertEquals(milestoneEndDate, dto.milestoneEndDate());
    }

    @Test
    void testMilestoneUpdateRequestDtoGetters() {
        Long milestoneId = 1L;
        String milestoneName = "Test Milestone";
        ZonedDateTime milestoneStartDate = ZonedDateTime.now().minusDays(1);
        ZonedDateTime milestoneEndDate = ZonedDateTime.now().plusDays(1);

        MilestoneUpdateRequestDto dto = new MilestoneUpdateRequestDto(
                milestoneId,
                milestoneName,
                milestoneStartDate,
                milestoneEndDate
        );

        assertNotNull(dto);
        assertEquals(milestoneId, dto.milestoneId());
        assertEquals(milestoneName, dto.milestoneName());
        assertEquals(milestoneStartDate, dto.milestoneStartDate());
        assertEquals(milestoneEndDate, dto.milestoneEndDate());
    }
}
