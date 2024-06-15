package com.example.demo.service.impl;

import com.example.demo.dto.CommentGetDto;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Task;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.request.CreateCommentRequest;
import com.example.demo.request.UpdateCommentRequest;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    @Override
    public List<CommentGetDto> getCommentsByTaskId(Long taskId) {
        List<CommentGetDto> commentGetDtoList = new ArrayList<>();

        for (Comment comment : commentRepository.findAllByTaskTaskId(taskId)) {
            CommentGetDto commentGetDto = CommentGetDto.builder()
                    .commentId(comment.getCommentId())
                    .comment(comment.getCommentContent())
                    .build();
            commentGetDtoList.add(commentGetDto);
        }

        return commentGetDtoList;
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    @Override
    public void createComment(CreateCommentRequest createCommentRequest, Long taskId) {
        Task task = taskRepository.findById(createCommentRequest.taskId()).orElse(null);
        if (task == null) {
            // Handle task not found
            throw new IllegalArgumentException("Invalid Task ID");
        }
        Comment comment = new Comment(createCommentRequest.commentContent(), task);
        commentRepository.save(comment);
    }

    @Override
    public void updateComment(Long commentId, UpdateCommentRequest updateCommentRequest) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) {
            // Handle comment not found
            throw  new IllegalArgumentException("Invalid Comment ID");

        }
        comment.setCommentContent(updateCommentRequest.commentContent());
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
        }
    }
}
