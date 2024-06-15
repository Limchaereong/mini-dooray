package com.nhnacademy.minidooraydgateway.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Builder
@Data
public class Milestone {

    private Long milestoneId;

    @NotBlank
    private String milestoneName;

    private ZonedDateTime milestoneStartDate;

    private ZonedDateTime milestoneEndDate;

    private long projectId;

}
