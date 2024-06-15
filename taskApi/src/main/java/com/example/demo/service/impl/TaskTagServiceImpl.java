package com.example.demo.service.impl;

import com.example.demo.entity.Tag;
import com.example.demo.entity.Task;
import com.example.demo.entity.TaskTag;
import com.example.demo.entity.TaskTagPk;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.TaskTagRepository;
import com.example.demo.service.TaskTagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskTagServiceImpl implements TaskTagService {
    private final TaskTagRepository taskTagRepository;
    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;

    @Override
    public TaskTag createTaskTag(TaskTag taskTag) {
        Task task = taskRepository.findById(taskTag.getTaskTagPk().getTaskId())
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        Tag tag = tagRepository.findById(taskTag.getTaskTagPk().getTagId())
                .orElseThrow(() -> new EntityNotFoundException("Tag not found"));

        // Task와 Tag 객체를 taskTag에 설정
        taskTag.setTask(task);
        taskTag.setTag(tag);

        return taskTagRepository.save(taskTag);
    }

    @Override
    public void deleteTaskTag(TaskTagPk taskTagPk) {
        taskTagRepository.deleteById(taskTagPk);
    }

    @Override
    public List<TaskTag> getAllTaskTags() {
        return taskTagRepository.findAll();
    }

    @Override
    public List<TaskTag> getTaskTagsByTaskId(Long taskId) {
        return taskTagRepository.findAll().stream().filter(tt -> tt.getTask().getTaskId().equals(taskId)).toList();
    }

    @Override
    public List<TaskTag> getTaskTagsByTagId(Long tagId) {
        return taskTagRepository.findAll().stream().filter(tt -> tt.getTag().getId().equals(tagId)).toList();
    }
}
