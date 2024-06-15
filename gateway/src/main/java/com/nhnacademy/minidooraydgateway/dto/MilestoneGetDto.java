package com.nhnacademy.minidooraydgateway.dto;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record MilestoneGetDto( String milestoneName,
                               Long milestoneId,
                               ZonedDateTime milestoneStartDate,
                               ZonedDateTime milestoneEndDate) {

}
