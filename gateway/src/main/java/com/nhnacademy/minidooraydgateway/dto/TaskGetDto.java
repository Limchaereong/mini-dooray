package com.nhnacademy.minidooraydgateway.dto;


import com.nhnacademy.minidooraydgateway.domain.Task;
import lombok.Builder;

@Builder
public record TaskGetDto(Long id, String name, String description, Task.TaskStatus status, Long projectId, Long milestoneId) {
    private static final long serialVersionUID = 1L;
}
