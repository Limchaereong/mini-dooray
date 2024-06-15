package com.example.demo.service.impl;

import com.example.demo.dto.MilestoneCreateRequestDto;
import com.example.demo.dto.MilestoneGetByMilestoneIdRequestDto;
import com.example.demo.dto.MilestoneGetDto;
import com.example.demo.dto.MilestoneUpdateRequestDto;
import com.example.demo.entity.Project;
import com.example.demo.entity.Milestone;
import com.example.demo.repository.MilestoneRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.MilestoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MilestoneServiceImpl implements MilestoneService {
    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @Override
    public List<MilestoneGetDto> getMilestonesByProjectId(Long projectId) {
        List<MilestoneGetDto> milestones = new ArrayList<>();
        for (Milestone milestone : milestoneRepository.findAllByProjectProjectId(projectId)) {
            MilestoneGetDto milestoneGetDto = MilestoneGetDto.builder()
                    .milestoneId(milestone.getMilestoneId())
                    .milestoneName(milestone.getMilestoneName())
                    .milestoneStartDate(milestone.getMilestoneStartDate())
                    .milestoneEndDate(milestone.getMilestoneEndDate())
                    .build();
            milestones.add(milestoneGetDto);
        }
        return milestones;
    }

    @Override
    public void createMilestoneById(MilestoneCreateRequestDto createMilestoneRequest, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project ID"));

        Milestone milestone = new Milestone(
                createMilestoneRequest.milestoneName(),
                createMilestoneRequest.milestoneStartDate(),
                createMilestoneRequest.milestoneEndDate(),
                project
        );
        milestoneRepository.save(milestone);

    }

    @Override
    public void changeMilestoneById(MilestoneUpdateRequestDto createMilestoneRequest, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project ID"));

        Milestone milestone = milestoneRepository.findById(createMilestoneRequest.milestoneId()).orElseThrow(() -> new IllegalArgumentException("Invalid milestone ID"));
        milestone.setMilestoneName(createMilestoneRequest.milestoneName());
        milestone.setMilestoneStartDate(createMilestoneRequest.milestoneStartDate());
        milestoneRepository.save(milestone);


    }

    @Override
    public void deleteMilestoneById(MilestoneGetByMilestoneIdRequestDto milestoneRequest) {
        Long milestoneId = milestoneRequest.milestoneId();
        boolean hasTasks = taskRepository.existsByMilestone_MilestoneId(milestoneId);
        if (hasTasks) {
            throw new IllegalStateException("Cannot delete milestone with existing tasks.");
        }
        milestoneRepository.deleteById(milestoneId);
    }


}
