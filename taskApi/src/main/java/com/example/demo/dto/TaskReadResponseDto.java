package com.example.demo.dto;

import com.example.demo.entity.Task;
import lombok.Builder;

@Builder
public record TaskReadResponseDto(Task task) {
}
