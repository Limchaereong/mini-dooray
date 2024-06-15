package com.nhnacademy.minidooraydgateway.dto;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record MilestoneUpdateDto(String milestoneName,
                                 ZonedDateTime milestoneStartDate,
                                 ZonedDateTime milestoneEndDate
                                 ) {
}
