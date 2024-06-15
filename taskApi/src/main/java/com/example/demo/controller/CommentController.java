package com.example.demo.controller;

import com.example.demo.dto.CommentGetDto;
import com.example.demo.request.CreateCommentRequest;
import com.example.demo.request.UpdateCommentRequest;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/projects/{projectId}/tasks/{taskId}/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentGetDto>> getComments(@PathVariable Long projectId, @PathVariable Long taskId) {

        List<CommentGetDto> comments = commentService.getCommentsByTaskId(taskId);
        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }


    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CreateCommentRequest createCommentRequest, @PathVariable Long projectId, @PathVariable Long taskId) {
       commentService.createComment(createCommentRequest, taskId);
       return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@RequestBody UpdateCommentRequest updateCommentRequest, @PathVariable Long projectId, @PathVariable Long taskId, @PathVariable Long commentId) {
        commentService.updateComment(commentId, updateCommentRequest);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long projectId, @PathVariable Long taskId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
