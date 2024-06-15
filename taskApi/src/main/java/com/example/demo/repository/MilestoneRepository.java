package com.example.demo.repository;

import com.example.demo.entity.Milestone;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {

    List<Milestone> findAllByProjectProjectId(Long projectId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Milestone m " +
            "set m.milestoneName = :milestoneName, " +
            "m.milestoneStartDate = :milestoneStartDate, m.milestoneEndDate = :milestoneEndDate " +
            "where m.milestoneId = :milestoneId and m.project.projectId = :projectId ")
    void updateProjectByProjectId(@Param("projectId") Long projectId, @Param("milestoneId") Long milestoneId
            , @Param("milestoneName") String milestoneName
            ,
                                  @Param("milestoneStartDate") ZonedDateTime milestoneStartDate
            , @Param("milestoneEndDate") ZonedDateTime milestoneEndDate);

}
