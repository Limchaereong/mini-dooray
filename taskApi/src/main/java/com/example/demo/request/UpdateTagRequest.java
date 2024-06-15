package com.example.demo.request;

import jakarta.validation.constraints.NotNull;


public record UpdateTagRequest(@NotNull String tagName,
                               Long projectId) {}
