package com.nhnacademy.minidooraydgateway.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Tag {
    private Long id;

    @NotNull(message = "Tag name cannot be null")
    private String name;

    private Long projectId;
}