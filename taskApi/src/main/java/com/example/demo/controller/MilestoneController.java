package com.example.demo.controller;


import com.example.demo.dto.MilestoneCreateRequestDto;
import com.example.demo.dto.MilestoneGetByMilestoneIdRequestDto;
import com.example.demo.dto.MilestoneGetDto;
import com.example.demo.dto.MilestoneUpdateRequestDto;
import com.example.demo.service.MilestoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects/{projectId}/milestones")
public class MilestoneController {

    private final MilestoneService milestoneService;


    @GetMapping
    public ResponseEntity<List<MilestoneGetDto>> getAllMilestones(@PathVariable Long projectId) {

        List<MilestoneGetDto> milestones = milestoneService.getMilestonesByProjectId(projectId);
        return ResponseEntity.status(HttpStatus.OK).body(milestones);
    }

    @PostMapping
    public ResponseEntity<Void> createMilestone(@RequestBody MilestoneCreateRequestDto createMilestoneRequest,
                                                @PathVariable Long projectId) {
        milestoneService.createMilestoneById(createMilestoneRequest, projectId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping
    public ResponseEntity<Void> updateMilestone(@PathVariable("projectId") Long projectId, @RequestBody MilestoneUpdateRequestDto milestone) {
        milestoneService.changeMilestoneById(milestone, projectId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping
    public void deleteMilestone(@PathVariable("projectId") Long projectId, @RequestBody MilestoneGetByMilestoneIdRequestDto milestoneGetByMilestoneIdRequestDto) {
        milestoneService.deleteMilestoneById(milestoneGetByMilestoneIdRequestDto);
    }
}
