package com.example.demo.repository;

import com.example.demo.entity.Project;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    //    @Modifying(clearAutomatically = true)
//    @Transactional
//    @Query("update Project p set p.projectName = :projectName, p.projectStatus = :projectStatus where p.projectId = :projectId")
//    void updateProjectByProjectId(@Param("projectId") Long projectId, @Param("projectName")String projectName, @Param("projectStatus") Project.ProjectStatus projectStatus);
//

    Page<Project> findAllByProjectIdIn(Collection<Long> projectIds, Pageable pageable);

    Project findFirstByProjectId(Long projectId);
}
