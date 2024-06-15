package com.example.demo.repository;

import com.example.demo.entity.Milestone;
import com.example.demo.entity.Project;
import com.example.demo.entity.Tag;
import com.example.demo.entity.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    @Modifying
    @Transactional
    @Query("update Tag t set t.name = :tagName, t.project = :project where t.id = :tagId")
    void updateByTagId(@Param("tagId") Long tagId,
                        @Param("tagName") String tagName,
                        @Param("project") Project project);

    List<Tag> findByProjectProjectId(Long projectId);

    Optional<Tag> findByNameAndProjectProjectId(String name, Long projectId);
}
