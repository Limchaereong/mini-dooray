package com.example.demo.dto;

import lombok.Builder;

@Builder
public record CommentGetDto(Long commentId,String userId, String comment){
}
