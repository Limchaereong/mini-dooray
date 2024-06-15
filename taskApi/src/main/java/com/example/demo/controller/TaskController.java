package com.example.demo.controller;

import com.example.demo.dto.TaskCreateDto;
import com.example.demo.dto.TaskGetDto;
import com.example.demo.request.TaskUpdateRequest;
import com.example.demo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskGetDto>> getTasks(@PathVariable("projectId") Long projectId) {

        List<TaskGetDto> tasks = taskService.findAllTask(projectId);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskGetDto> getTask(@PathVariable Long taskId) {
        TaskGetDto taskReadResponseDto = taskService.getById(taskId);
        return ResponseEntity.status(HttpStatus.OK).body(taskReadResponseDto);
    }

    @PostMapping
    public ResponseEntity<TaskCreateDto> createTask(@RequestBody TaskCreateDto request, @PathVariable Long projectId) {
        TaskCreateDto taskCreateDto = taskService.create(request, projectId);
        return ResponseEntity.status(HttpStatus.OK).body(taskCreateDto);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Void> updateTask(@RequestBody TaskUpdateRequest request, @PathVariable Long taskId) {
        try {
            taskService.modifyById(request, taskId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        taskService.deleteById(taskId);
    }

}
