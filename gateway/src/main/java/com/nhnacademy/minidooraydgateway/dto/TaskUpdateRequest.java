package com.nhnacademy.minidooraydgateway.dto;


import com.nhnacademy.minidooraydgateway.domain.Task;
import jakarta.validation.constraints.NotNull;


public record TaskUpdateRequest(@NotNull String name,
                                String description,
                                Task.TaskStatus status,
                                Long milestoneId) {}
