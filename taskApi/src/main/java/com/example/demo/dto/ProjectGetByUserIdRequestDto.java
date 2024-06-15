package com.example.demo.dto;

import lombok.Builder;

@Builder
public record ProjectGetByUserIdRequestDto(long userId) {
}
