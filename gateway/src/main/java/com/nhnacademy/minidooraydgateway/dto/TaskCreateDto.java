package com.nhnacademy.minidooraydgateway.dto;

import com.nhnacademy.minidooraydgateway.domain.Task;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record TaskCreateDto(String name, String description, Task.TaskStatus status, Long milestoneId) implements Serializable {
    private static final long serialVersionUID = 1L;
}
