package com.example.demo.request;

import com.example.demo.entity.Task;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Value;


public record CreateTaskRequest(@NotNull String taskName,
                                String taskDescription,
                                @Enumerated(EnumType.STRING) Task.TaskStatus taskStatus,
                                Long milestoneId) {}
