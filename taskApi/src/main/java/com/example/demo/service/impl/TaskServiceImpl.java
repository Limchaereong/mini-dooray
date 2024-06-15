package com.example.demo.service.impl;

import com.example.demo.dto.TaskCreateDto;
import com.example.demo.dto.TaskGetDto;
import com.example.demo.request.TaskUpdateRequest;
import com.example.demo.entity.Milestone;
import com.example.demo.entity.Project;
import com.example.demo.entity.Task;
import com.example.demo.error.ResourceNotFoundException;
import com.example.demo.repository.MilestoneRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;

    @Override
    public List<TaskGetDto> findAllTask(Long projectId) {
        List<Task> taskPage = taskRepository.findAllByProjectProjectId(projectId);
        List<TaskGetDto> taskGetDtos = new ArrayList<>();

        for(Task task : taskPage) {
            TaskGetDto taskGetDto = TaskGetDto.builder()
                    .id(task.getTaskId())
                    .name(task.getTaskName())
                    .description(task.getTaskDescription())
                    .status(task.getTaskStatus())
                    .projectId(task.getProject().getProjectId())
                    .build();
            if(task.getMilestone() != null)
            {
                TaskGetDto.builder().milestoneId(task.getMilestone().getMilestoneId()).build();
            }
            taskGetDtos.add(taskGetDto);
        }
        return taskGetDtos;
    }

    @Override
    public TaskGetDto getById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task(id = " + taskId + ") not found."));

        TaskGetDto taskCreateDto =  TaskGetDto.builder()
                .id(task.getTaskId())
                .name(task.getTaskName())
                .description(task.getTaskDescription())
                .status(task.getTaskStatus())
                .projectId(task.getProject().getProjectId())
                .build();

        if (task.getMilestone() != null){
            TaskGetDto.builder().milestoneId(task.getMilestone().getMilestoneId()).build();
        }

        return taskCreateDto;

    }

    @Override
    public TaskCreateDto create(TaskCreateDto request, Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project(id = " + projectId + ") not found."));

        Milestone milestone = null;
        if(request.milestoneId() != null){
            milestone = milestoneRepository.findById(request.milestoneId()).orElse(null);
        }

        Task task = new Task(
                request.name(),
                request.description(),
                request.status(),
                project,
                milestone
        );

        taskRepository.save(task);


        return TaskCreateDto.builder()
                .name(task.getTaskName())
                .description(task.getTaskDescription())
                .status(task.getTaskStatus())
                .milestoneId(Objects.isNull(task.getMilestone()) ? null: task.getMilestone().getMilestoneId())
                .build();

    }

    @Override
    public void modifyById(TaskUpdateRequest request, Long taskId) {
        if(!taskRepository.existsById(taskId)){
            throw new IllegalArgumentException("Task id " + taskId + " not exists.");
        }

        Milestone milestone = null;
        if(request.milestoneId() != null){
            milestone = milestoneRepository.findById(request.milestoneId()).orElse(null);
        }

        taskRepository.updateByTaskId(
                taskId,
                request.name(),
                request.description(),
                request.status(),
                milestone);
    }

    @Override
    public void deleteById(Long id) {
        if(!taskRepository.existsById(id)){
            throw new IllegalArgumentException("Task id " + id + " not exists.");
        }
        taskRepository.deleteById(id);
    }
}
