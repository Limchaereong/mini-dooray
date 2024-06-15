package com.nhnacademy.minidooraydgateway.controller;

import com.nhnacademy.minidooraydgateway.domain.Comment;
import com.nhnacademy.minidooraydgateway.dto.CommentGetDto;
import com.nhnacademy.minidooraydgateway.dto.CreateCommentRequest;
import com.nhnacademy.minidooraydgateway.dto.UpdateCommentRequest;
import com.nhnacademy.minidooraydgateway.exception.LoginRequiredException;
import com.nhnacademy.minidooraydgateway.service.CommentService;
import com.nhnacademy.minidooraydgateway.util.SecurityContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/projects/{projectId}/tasks/{taskId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final SecurityContextUtil securityContextUtil;


    // Comment 생성 페이지
    @GetMapping
    public String getNewCommentPage(@PathVariable Long projectId, @PathVariable Long taskId, Model model) {
        List<CommentGetDto> comments = commentService.getTaskComments(projectId,taskId);


        model.addAttribute("comments", comments);

        model.addAttribute("projectId", projectId);
        model.addAttribute("taskId", taskId);
        return "project/comments";
    }


    // Comment 생성 처리
    @PostMapping
    public String handleCreateComment(@PathVariable Long projectId,
                                      @PathVariable Long taskId,
                                      @RequestParam("comment") String comment) {
        CreateCommentRequest commentRequest = CreateCommentRequest.builder()
                .commentContent(comment)
                .taskId(taskId)
                .build();
        commentService.createComment(projectId, taskId, commentRequest);
        return "redirect:/projects/" + projectId + "/tasks/" + taskId+"/comments";
    }

    // Comment 수정 처리
    @PostMapping("/{commentId}/edit")
    public String handleEditComment(@PathVariable Long projectId, @PathVariable Long taskId, @PathVariable Long commentId, @RequestParam("update_comment") String comment) {
        UpdateCommentRequest updateCommentRequest = UpdateCommentRequest.builder()
                .commentContent(comment)
                .taskId(taskId)
                .build();
        commentService.updateComment(updateCommentRequest,projectId, taskId, commentId);
        return "redirect:/projects/" + projectId + "/tasks/" + taskId+"/comments";
    }

    // Comment 삭제 처리
    @PostMapping("/{commentId}/delete")
    public String handleDeleteComment(@PathVariable Long projectId, @PathVariable Long taskId, @PathVariable Long commentId) {
        commentService.deleteComment(projectId,taskId,commentId);
        return "redirect:/projects/" + projectId + "/tasks/" + taskId+"/comments";
    }
}