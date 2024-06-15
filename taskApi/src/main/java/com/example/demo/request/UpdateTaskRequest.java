package com.example.demo.request;

import com.example.demo.entity.Task;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;


public record UpdateTaskRequest(@NotNull String taskName,
                                String taskDescription,
                                @Enumerated(EnumType.STRING) Task.TaskStatus taskStatus,
                                Long milestoneId) {}
