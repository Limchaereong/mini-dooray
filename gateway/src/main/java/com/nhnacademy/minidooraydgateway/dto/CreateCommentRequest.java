package com.nhnacademy.minidooraydgateway.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateCommentRequest(@NotNull String commentContent, @NotNull Long taskId) {
}
