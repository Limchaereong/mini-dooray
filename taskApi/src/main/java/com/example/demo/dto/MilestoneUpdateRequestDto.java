package com.example.demo.dto;


import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record MilestoneUpdateRequestDto(
        Long milestoneId,
        String milestoneName,
        ZonedDateTime milestoneStartDate,
        ZonedDateTime milestoneEndDate){}
