package com.nhnacademy.minidooraydgateway.client;

import com.nhnacademy.minidooraydgateway.domain.*;
import com.nhnacademy.minidooraydgateway.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "projectService", url = "${api.project-service.base-url}")
public interface ProjectServiceClient {

    // 특정 유저의 프로젝트 목록 조회
    @GetMapping("/projects")
    ResponseEntity<Page<ProjectGetDto>> getAllProjectsByUserId(@RequestParam Long userId,
                                                               @RequestParam("page") int page,
                                                               @RequestParam("size") int size,
                                                               @RequestParam(required = false) String sort);

    // Project
    // 특정 프로젝트 조회
    @GetMapping("/projects/{projectId}")
    ResponseEntity<ProjectGetDto> getProjectById(@PathVariable("projectId") Long projectId);

    // 프로적트 생성
    @PostMapping("/projects")
    ResponseEntity<Project> createProject(@RequestBody ProjectCreateDto projectCreateDto);


    @PutMapping("/projects/{projectId}/status")
    ResponseEntity<Void> updateProjectStatus(@PathVariable Long projectId, @RequestBody ProjectStatusUpdateDto projectStatusUpdateDto);

    // ProjectMember
    // Member for Project
    @PostMapping("/projects/{projectId}/members")
    ResponseEntity<Milestone> addMemberToProject(@PathVariable("projectId") Long projectId,
                                                 @RequestBody ProjectMemberDto memberIds);

    // 멤버 Ids 가져옴
    @GetMapping("/projects/{projectId}/members")
    ResponseEntity<List<Long>> getProjectMembers(@PathVariable("projectId") Long projectId);

    // Task
    @GetMapping("/projects/{projectId}/tasks")
    ResponseEntity<List<TaskGetDto>> getAllTasks(@PathVariable("projectId") Long projectId);

    @GetMapping("/projects/{projectId}/tasks/{taskId}")
    ResponseEntity<TaskGetDto> getTaskByTaskId(@PathVariable("projectId") Long projectId,
                                               @PathVariable("taskId") Long taskId);

    @PostMapping("/projects/{projectId}/tasks")
    ResponseEntity<TaskCreateDto> createTask(@PathVariable("projectId") Long projectId,
                                             @RequestBody TaskCreateDto request);

    @PutMapping("/projects/{projectId}/tasks/{taskId}")
    ResponseEntity<Void> updateTask(@PathVariable("projectId") Long projectId,
                                    @PathVariable("taskId") Long taskId,
                                    @ModelAttribute TaskUpdateRequest request);

    @DeleteMapping("/projects/{projectId}/tasks/{taskId}")
    ResponseEntity<Void> deleteTask(@PathVariable("projectId") Long projectId,
                                    @PathVariable("taskId") Long taskId);

    //comments
    @PostMapping("/projects/{projectId}/tasks/{taskId}/comments")
    ResponseEntity<Void> createComment(@PathVariable("projectId") Long projectId,
                                       @PathVariable("taskId") Long taskId,
                                       @RequestBody CreateCommentRequest request);
    @PutMapping("/projects/{projectId}/tasks/{taskId}/comments/{commentId}")
    ResponseEntity<Void> updateComment(@RequestBody UpdateCommentRequest updateCommentRequest, @PathVariable Long projectId, @PathVariable Long taskId, @PathVariable Long commentId);

    @GetMapping("/projects/{projectId}/tasks/{taskId}/comments")
    ResponseEntity<List<CommentGetDto>> getTaskComments(@PathVariable("projectId") Long projectId,
                                                        @PathVariable("taskId") Long taskId);

    @DeleteMapping("/projects/{projectId}/tasks/{taskId}/comments/{commentId}")
    ResponseEntity<Void> deleteComment(@PathVariable("projectId") Long projectId,
                                       @PathVariable("taskId") Long taskId,
                                       @PathVariable("commentId") Long commentId);



    @GetMapping("/projects/{projectId}/tags")
    ResponseEntity<Page<Tag>> getProjectTags(@PathVariable("projectId") Long projectId,
                                             @RequestParam("page") int page,
                                             @RequestParam("size") int size);

    @DeleteMapping("/projects/{projectId}/tags/{tagId}")
    ResponseEntity<Void> deleteTag(@PathVariable("projectId") Long projectId,
                                   @PathVariable("tagId") String tagId);

    @GetMapping("/projects/{projectId}/milestones")
    ResponseEntity<Page<Milestone>> getProjectMilestones(@PathVariable("projectId") Long projectId,
                                                         @RequestParam("page") int page,
                                                         @RequestParam("size") int size,
                                                         @RequestParam(required = false) String sort);

    @DeleteMapping("/projects/{projectId}/milestones/{milestoneId}")
    ResponseEntity<Void> deleteMilestone(@PathVariable("projectId") Long projectId,
                                         @PathVariable("milestoneId") String milestoneId);

    @PostMapping("/projects/{projectId}/tasks/{taskId}/tags/{tagId}")
    ResponseEntity<Void> addTagToTask(@PathVariable("projectId") Long projectId,
                                      @PathVariable("taskId") String taskId,
                                      @PathVariable("tagId") String tagId);

    @DeleteMapping("/projects/{projectId}/tasks/{taskId}/tags/{tagId}")
    ResponseEntity<Void> removeTagFromTask(@PathVariable("projectId") Long projectId,
                                           @PathVariable("taskId") String taskId,
                                           @PathVariable("tagId") String tagId);

    @PostMapping("/projects/{projectId}/tasks/{taskId}/milestones/{milestoneId}")
    ResponseEntity<Void> addMilestoneToTask(@PathVariable("projectId") Long projectId,
                                            @PathVariable("taskId") String taskId,
                                            @PathVariable("milestoneId") String milestoneId);

    @DeleteMapping("/projects/{projectId}/tasks/{taskId}/milestones/{milestoneId}")
    ResponseEntity<Void> removeMilestoneFromTask(@PathVariable("projectId") Long projectId,
                                                 @PathVariable("taskId") String taskId,
                                                 @PathVariable("milestoneId") String milestoneId);


    // 마일스톤
    // Milestone for Project
    @PostMapping("/projects/{projectId}/milestones")
    ResponseEntity<Void> createMilestone(@PathVariable("projectId") Long projectId,
                                         @RequestBody MilestoneCreateRequestDto createMilestoneRequest);

    @GetMapping("/projects/{projectId}/milestones")
    ResponseEntity<List<MilestoneGetDto>> getProjectMilestones(@PathVariable("projectId") Long projectId);


    @PutMapping("/projects/{projectId}/milestones")
    ResponseEntity<Void> updateMilestone(@PathVariable("projectId") Long projectId,
                                         @RequestBody MilestoneUpdateRequestDto milestone);

    @DeleteMapping("/projects/{projectId}/milestones")
    ResponseEntity<Void> deleteMilestone(@PathVariable("projectId") Long projectId,
                                         @RequestBody MilestoneGetByMilestoneIdRequestDto milestoneGetByMilestoneIdRequestDto);

    // Milestone for Task
    @PostMapping("/projects/{projectId}/tasks/{taskId}/milestones")
    void setTaskMilestone(@PathVariable("projectId") Long projectId,
                          @PathVariable("taskId") Long taskId,
                          @RequestBody Long milestoneId);

    @PostMapping("/projects/{projectId}/tasks/{taskId}/milestones")
    void deleteTaskMilestone(@PathVariable("projectId") Long projectId,
                             @PathVariable("taskId") Long taskId);


    // 태그
    // Tag for Project
    @GetMapping("/projects/{projectId}/tags")
    List<Tag> getTagsByProjectId(@PathVariable("projectId") Long projectId);

    @GetMapping("/projects/{projectId}/tags/{tagId}")
    Tag getTagById(@PathVariable("projectId") Long projectId, @PathVariable("tagId") Long tagId);

    @PostMapping("/projects/{projectId}/tags")
    void createTag(@PathVariable("projectId") Long projectId, @RequestBody Tag tag);

    @PutMapping("/projects/{projectId}/tags/{tagName}/name")
    void updateTagName(@PathVariable("projectId") Long projectId, @PathVariable("tagName") String tagName, @RequestBody String newTagName);

    @DeleteMapping("/projects/{projectId}/tags/{tagId}")
    void deleteTag(@PathVariable("projectId") Long projectId, @PathVariable("tagId") Long tagId);

    // Tag for Task
    @PostMapping("/projects/{projectId}/tasks/{taskId}/tags")
    void setTaskTag(@PathVariable("projectId") Long projectId,
                    @PathVariable("taskId") Long taskId,
                    @RequestBody Long tagId);

    @PostMapping("/projects/{projectId}/tasks/{taskId}/tags")
    void deleteTaskTag(@PathVariable("projectId") Long projectId,
                       @PathVariable("taskId") Long taskId);


}