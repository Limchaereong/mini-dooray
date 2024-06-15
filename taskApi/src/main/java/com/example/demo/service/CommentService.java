package com.example.demo.service;

import com.example.demo.dto.CommentGetDto;
import com.example.demo.entity.Comment;
import com.example.demo.request.CreateCommentRequest;
import com.example.demo.request.UpdateCommentRequest;

import java.util.List;

public interface CommentService {
    List<CommentGetDto> getCommentsByTaskId(Long taskId);
    Comment getCommentById(Long commentId);
    void createComment(CreateCommentRequest createCommentRequest, Long taskId);
    void updateComment(Long commentId, UpdateCommentRequest updateCommentRequest);
    void deleteComment(Long commentId);
}
