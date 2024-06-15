package com.nhnacademy.minidooraydgateway.service;

import com.nhnacademy.minidooraydgateway.client.ProjectServiceClient;
import com.nhnacademy.minidooraydgateway.domain.Comment;
import com.nhnacademy.minidooraydgateway.dto.CommentGetDto;
import com.nhnacademy.minidooraydgateway.dto.CreateCommentRequest;
import com.nhnacademy.minidooraydgateway.dto.UpdateCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ProjectServiceClient projectServiceClient;


    public void createComment(Long projectId, Long taskId, CreateCommentRequest comment) {
        projectServiceClient.createComment(projectId, taskId, comment);
    }
    public void updateComment(UpdateCommentRequest comment, Long projectId, Long taskId, Long commentId) {
        projectServiceClient.updateComment(comment, projectId, taskId ,commentId);
    }
    public void deleteComment(Long projectId, Long taskId, Long commentId) {
        projectServiceClient.deleteComment(projectId,taskId,commentId);
    }

    public List<CommentGetDto> getTaskComments(Long projectId, Long taskId) {
        ResponseEntity<List<CommentGetDto>> response = projectServiceClient.getTaskComments(projectId, taskId);
        return response.getBody();
    }

}
