package com.example.demo.service;

import com.example.demo.dto.MilestoneCreateRequestDto;
import com.example.demo.dto.MilestoneGetByMilestoneIdRequestDto;
import com.example.demo.dto.MilestoneGetDto;
import com.example.demo.dto.MilestoneUpdateRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MilestoneService {
    List<MilestoneGetDto> getMilestonesByProjectId(Long projectId);
    void createMilestoneById(MilestoneCreateRequestDto createMilestoneRequest, Long projectId);
    void changeMilestoneById(MilestoneUpdateRequestDto createMilestoneRequest, Long projectId);
    void deleteMilestoneById(MilestoneGetByMilestoneIdRequestDto milestoneRequest);
}
