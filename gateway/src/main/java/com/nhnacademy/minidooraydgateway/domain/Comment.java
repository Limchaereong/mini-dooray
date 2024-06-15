package com.nhnacademy.minidooraydgateway.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Comment {
    private long id;

    private Long commentId;

    @NotBlank
    private String commentContent;
}
