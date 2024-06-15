package com.example.demo.controller;

import com.example.demo.entity.TaskTag;
import com.example.demo.entity.TaskTagPk;
import com.example.demo.service.TaskTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task-tags")
@RequiredArgsConstructor
public class TaskTagController {
    private final TaskTagService taskTagService;

    @PostMapping
    public ResponseEntity<TaskTag> createTaskTag(@RequestBody TaskTag taskTag) {
        TaskTag createdTaskTag = taskTagService.createTaskTag(taskTag);
        return ResponseEntity.ok(createdTaskTag);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTaskTag(@RequestBody TaskTagPk taskTagPk) {
        taskTagService.deleteTaskTag(taskTagPk);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TaskTag>> getAllTaskTags() {
        List<TaskTag> taskTags = taskTagService.getAllTaskTags();
        return ResponseEntity.ok(taskTags);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<TaskTag>> getTaskTagsByTaskId(@PathVariable Long taskId) {
        List<TaskTag> taskTags = taskTagService.getTaskTagsByTaskId(taskId);
        return ResponseEntity.ok(taskTags);
    }

    @GetMapping("/tag/{tagId}")
    public ResponseEntity<List<TaskTag>> getTaskTagsByTagId(@PathVariable Long tagId) {
        List<TaskTag> taskTags = taskTagService.getTaskTagsByTagId(tagId);
        return ResponseEntity.ok(taskTags);
    }
}
