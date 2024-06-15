package com.nhnacademy.minidooraydgateway.dto;

import lombok.Builder;

@Builder
public record CommentGetDto(Long commentId, String comment){
}
