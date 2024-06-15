package com.example.demo.dto;

import com.example.demo.entity.Task;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record TaskCreateDto(String name, String description, Task.TaskStatus status, Long milestoneId) implements Serializable {
    private static final long serialVersionUID = 1L;
}
