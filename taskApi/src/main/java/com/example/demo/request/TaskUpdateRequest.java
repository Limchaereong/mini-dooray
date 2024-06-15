package com.example.demo.request;


import com.example.demo.entity.Task;
import jakarta.validation.constraints.NotNull;


public record TaskUpdateRequest(@NotNull String name,
                                String description,
                                Task.TaskStatus status,
                                Long milestoneId) {}
