package com.nhnacademy.minidooraydgateway.service;

import com.nhnacademy.minidooraydgateway.client.ProjectServiceClient;
import com.nhnacademy.minidooraydgateway.dto.TaskCreateDto;
import com.nhnacademy.minidooraydgateway.dto.TaskGetDto;
import com.nhnacademy.minidooraydgateway.dto.TaskUpdateRequest;
import com.nhnacademy.minidooraydgateway.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final ProjectServiceClient projectServiceClient;

    public TaskGetDto getTaskByTaskId(Long projectId, Long taskId) {
        ResponseEntity<TaskGetDto> response = projectServiceClient.getTaskByTaskId(projectId, taskId);
        return response.getBody();
    }

    public List<TaskGetDto> getAllTasksByProjectId(Long projectId) {
        ResponseEntity<List<TaskGetDto>> response = projectServiceClient.getAllTasks(projectId);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }

    public void createTask(Long projectId, TaskCreateDto task) {
        projectServiceClient.createTask(projectId, task);
    }

    public void updateTask(Long projectId, Long taskId, TaskUpdateRequest task) {
        projectServiceClient.updateTask(projectId, taskId, task);
    }

    public void deleteTask(Long projectId, Long taskId) {
        projectServiceClient.deleteTask(projectId, taskId);
    }

    // 태그 설정
    public void setTaskTag(Long projectId, Long taskId, Long tagId) {
        projectServiceClient.setTaskTag(projectId, taskId, tagId);
    }

    public void deleteTaskTag(Long projectId, Long taskId) {
        projectServiceClient.deleteTaskTag(projectId, taskId);
    }


    // 마일스톤 설정
    public void setTaskMilestone(Long projectId, Long taskId, Long milestoneId) {
        projectServiceClient.setTaskMilestone(projectId, taskId, milestoneId);
    }

    public void deleteTaskMilestone(Long projectId, Long taskId) {
        projectServiceClient.deleteTaskMilestone(projectId, taskId);
    }


}
