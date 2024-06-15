package com.example.demo.dto;

import com.example.demo.entity.Task;
import lombok.Builder;

@Builder
public record TaskGetDto(Long id, String name, String description, Task.TaskStatus status, Long projectId, Long milestoneId) {
}
