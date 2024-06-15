package com.example.demo.dto;

import java.time.ZonedDateTime;


public record MilestoneCreateRequestDto(
                                      String milestoneName,
                                      ZonedDateTime milestoneStartDate,
                                      ZonedDateTime milestoneEndDate){}
