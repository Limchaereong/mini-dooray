package com.example.demo.dto;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MilestoneUpdateDtoTest {

    @Test
    void testMilestoneUpdateDtoBuilder() {
        String milestoneName = "Test Milestone";
        ZonedDateTime milestoneStartDate = ZonedDateTime.now().minusDays(1);
        ZonedDateTime milestoneEndDate = ZonedDateTime.now().plusDays(1);

        MilestoneUpdateDto dto = MilestoneUpdateDto.builder()
                .milestoneName(milestoneName)
                .milestoneStartDate(milestoneStartDate)
                .milestoneEndDate(milestoneEndDate)
                .build();

        assertNotNull(dto);
        assertEquals(milestoneName, dto.milestoneName());
        assertEquals(milestoneStartDate, dto.milestoneStartDate());
        assertEquals(milestoneEndDate, dto.milestoneEndDate());
    }

    @Test
    void testMilestoneUpdateDtoGetters() {
        String milestoneName = "Test Milestone";
        ZonedDateTime milestoneStartDate = ZonedDateTime.now().minusDays(1);
        ZonedDateTime milestoneEndDate = ZonedDateTime.now().plusDays(1);

        MilestoneUpdateDto dto = new MilestoneUpdateDto(milestoneName, milestoneStartDate, milestoneEndDate);

        assertNotNull(dto);
        assertEquals(milestoneName, dto.milestoneName());
        assertEquals(milestoneStartDate, dto.milestoneStartDate());
        assertEquals(milestoneEndDate, dto.milestoneEndDate());
    }
}
