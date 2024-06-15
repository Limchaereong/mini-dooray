package com.example.demo.request;

import jakarta.validation.constraints.NotNull;


public record UpdateCommentRequest(@NotNull String commentContent, @NotNull Long taskId) {
}
