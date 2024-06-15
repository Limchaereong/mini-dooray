package com.example.demo.service;

import com.example.demo.dto.TaskCreateDto;
import com.example.demo.dto.TaskGetDto;
import com.example.demo.request.TaskUpdateRequest;

import java.util.List;

public interface TaskService {
    List<TaskGetDto> findAllTask(Long projectId);

    TaskGetDto getById(Long taskId);

    TaskCreateDto create(TaskCreateDto request, Long projectId);

    void modifyById(TaskUpdateRequest updateTaskRequest, Long taskId);

    void deleteById(Long id);
}
