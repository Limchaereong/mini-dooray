package com.example.demo.service;

import com.example.demo.entity.TaskTag;
import com.example.demo.entity.TaskTagPk;

import java.util.List;

public interface TaskTagService {
    TaskTag createTaskTag(TaskTag taskTag);
    void deleteTaskTag(TaskTagPk taskTagPk);
    List<TaskTag> getAllTaskTags();
    List<TaskTag> getTaskTagsByTaskId(Long taskId);
    List<TaskTag> getTaskTagsByTagId(Long tagId);
}
