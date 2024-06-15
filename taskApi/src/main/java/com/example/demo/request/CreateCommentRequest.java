package com.example.demo.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
public record CreateCommentRequest(@NotNull String commentContent, @NotNull Long taskId) {
}
