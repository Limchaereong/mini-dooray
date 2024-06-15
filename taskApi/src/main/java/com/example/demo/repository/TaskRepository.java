package com.example.demo.repository;

import com.example.demo.entity.Milestone;
import com.example.demo.entity.Task;
import com.example.demo.request.UpdateTaskRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByProjectProjectId(Long projectId);

    Task findByProjectProjectIdAndTaskId(Long projectId, Long taskId);

    Boolean existsByMilestone_MilestoneId(Long milestoneId);

//    @Modifying
//    @Transactional
//    @Query("update Task t set t.taskName=:taskName, t.taskDescription=:taskDescription, t.TaskStatus=:taskStatus, t.milestone=:milestone where t.taskId=:taskId")
//    void updateByTaskId(Long taskId, String taskName, String taskDescription, Task.TaskStatus taskStatus, Milestone milestone);

    @Modifying
    @Transactional
    @Query("update Task t set t.taskName = :taskName, t.taskDescription = :taskDescription, t.taskStatus = :taskStatus, t.milestone = :milestone where t.taskId = :taskId")
    void updateByTaskId(@Param("taskId") Long taskId,
                        @Param("taskName") String taskName,
                        @Param("taskDescription") String taskDescription,
                        @Param("taskStatus") Task.TaskStatus taskStatus,
                        @Param("milestone") Milestone milestone);

}
