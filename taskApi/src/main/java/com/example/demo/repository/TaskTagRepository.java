package com.example.demo.repository;

import com.example.demo.entity.TaskTag;
import com.example.demo.entity.TaskTagPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTagRepository extends JpaRepository<TaskTag, TaskTagPk> {
}
