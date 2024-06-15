package com.example.demo.dto;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MilestoneCreateDtoTest {

    @Test
    void testMilestoneCreateDtoBuilder() {
        ZonedDateTime now = ZonedDateTime.now();
        MilestoneCreateDto milestoneCreateDto = MilestoneCreateDto.builder()
                .milestoneName("Milestone 1")
                .milestoneStartDate(now)
                .milestoneEndDate(now.plusDays(1))
                .build();

        assertNotNull(milestoneCreateDto);
        assertEquals("Milestone 1", milestoneCreateDto.milestoneName());
        assertEquals(now, milestoneCreateDto.milestoneStartDate());
        assertEquals(now.plusDays(1), milestoneCreateDto.milestoneEndDate());
    }

    @Test
    void testMilestoneCreateDtoGetters() {
        ZonedDateTime startDate = ZonedDateTime.now();
        ZonedDateTime endDate = startDate.plusDays(1);
        MilestoneCreateDto milestoneCreateDto = new MilestoneCreateDto("Milestone 2", startDate, endDate);

        assertNotNull(milestoneCreateDto);
        assertEquals("Milestone 2", milestoneCreateDto.milestoneName());
        assertEquals(startDate, milestoneCreateDto.milestoneStartDate());
        assertEquals(endDate, milestoneCreateDto.milestoneEndDate());
    }
}
