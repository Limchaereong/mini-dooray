package com.nhnacademy.minidooraydgateway.controller;

import com.nhnacademy.minidooraydgateway.domain.Milestone;
import com.nhnacademy.minidooraydgateway.dto.*;
import com.nhnacademy.minidooraydgateway.service.MilestoneService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;



@Controller
@RequestMapping("/projects/{projectId}/milestones")
@RequiredArgsConstructor
public class MilestoneController {

    private final MilestoneService milestoneService;

    // Milestone 목록 페이지
    @GetMapping
    public String getMilestonesPage(@PathVariable Long projectId,
                                    Model model) {
        List<MilestoneGetDto> milestones = milestoneService.getMilestonesByProjectId(projectId);
        model.addAttribute("milestones", milestones);
        model.addAttribute("projectId", projectId);
        return "project/milestoneProperty";
    }

    // Milestone 생성 페이지
    @GetMapping("/new")
    public String getNewMilestonePage(@PathVariable Long projectId, Model model) {
        model.addAttribute("milestone", Milestone.builder().build());
        model.addAttribute("projectId", projectId);
        return "project/milestonePropertyAdd";
    }
    @GetMapping("/update/{milestoneId}")
    public String getUpdateMilestonePage(@PathVariable Long projectId,@PathVariable Long milestoneId, Model model) {
        model.addAttribute("milestone", MilestoneUpdateRequestDto.builder().milestoneId(milestoneId).build());
        model.addAttribute("projectId", projectId);
        return "project/milestonePropertyUpdate";
    }

    // Milestone 생성 처리
    @PostMapping
    public String handleCreateMilestone(@PathVariable Long projectId,
                                        @RequestParam("milestoneName") String milestoneName,
                                        @RequestParam("milestoneStartDate") String milestoneStartDate,
                                        @RequestParam("milestoneEndDate") String milestoneEndDate) {

        ZonedDateTime milestoneStart = null;
        ZonedDateTime milestoneEnd = null;

        if (!StringUtils.isEmpty(milestoneStartDate)) {
            LocalDateTime tmpStartDate = LocalDateTime.parse(milestoneStartDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            milestoneStart = tmpStartDate.atZone(ZoneId.of("Asia/Seoul"));
        }

        if (!StringUtils.isEmpty(milestoneEndDate)) {
            LocalDateTime tmpEndDate = LocalDateTime.parse(milestoneEndDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            milestoneEnd = tmpEndDate.atZone(ZoneId.of("Asia/Seoul"));
        }

        MilestoneCreateRequestDto milestoneCreateRequestDto = MilestoneCreateRequestDto.builder()
                .milestoneName(milestoneName)
                .milestoneStartDate(milestoneStart)
                .milestoneEndDate(milestoneEnd)
                .build();
        milestoneService.createMilestone(projectId, milestoneCreateRequestDto);
        return "redirect:/projects/" + projectId + "/milestones";
    }

//    // Milestone 수정 페이지
//    @GetMapping("/{milestoneId}/edit")
//    public String getEditMilestonePage(@PathVariable Long projectId, @PathVariable Long milestoneId, Model model) {
//        Milestone milestone = milestoneService.getMilestoneById(projectId, milestoneId);
//        model.addAttribute("milestone", milestone);
//        model.addAttribute("projectId", projectId);
//        return "milestone/editMilestone";
//    }

    // Milestone 수정 처리
    @PostMapping("/update/{milestoneId}")
    public String handleEditMilestone(@PathVariable Long projectId,
                                      @PathVariable Long milestoneId,
                                      @RequestParam("milestoneName") String milestoneName,
                                      @RequestParam("milestoneStartDate") String milestoneStartDate,
                                      @RequestParam("milestoneEndDate") String milestoneEndDate) {
        ZonedDateTime milestoneStart = null;
        ZonedDateTime milestoneEnd = null;

        if (!StringUtils.isEmpty(milestoneStartDate)) {
            LocalDateTime tmpStartDate = LocalDateTime.parse(milestoneStartDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            milestoneStart = tmpStartDate.atZone(ZoneId.of("Asia/Seoul"));
        }

        if (!StringUtils.isEmpty(milestoneEndDate)) {
            LocalDateTime tmpEndDate = LocalDateTime.parse(milestoneEndDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
            milestoneEnd = tmpEndDate.atZone(ZoneId.of("Asia/Seoul"));
        }
        MilestoneUpdateRequestDto milestoneUpdateRequestDto = MilestoneUpdateRequestDto.builder()
                .milestoneId(milestoneId)
                .milestoneName(milestoneName)
                .milestoneStartDate(milestoneStart)
                .milestoneEndDate(milestoneEnd)
                .build();

        milestoneService.updateMilestone(projectId, milestoneUpdateRequestDto);
        return "redirect:/projects/" + projectId + "/milestones";
    }

    // Milestone 삭제 처리
    @PostMapping("/delete/{milestoneId}")
    public String handleDeleteMilestone(@PathVariable Long projectId, @PathVariable Long milestoneId) {
        milestoneService.deleteMilestone(projectId, MilestoneGetByMilestoneIdRequestDto.builder().milestoneId(milestoneId).build());
        return "redirect:/projects/" + projectId + "/milestones";
    }
}