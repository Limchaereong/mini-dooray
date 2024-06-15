package com.example.demo.dto;

import lombok.Builder;

@Builder
public record MilestoneGetByUserIdRequestDto(long userId) {
}
