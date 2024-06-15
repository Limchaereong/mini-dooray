

package com.nhnacademy.minidooraydgateway.service;
import com.nhnacademy.minidooraydgateway.client.ProjectServiceClient;
import com.nhnacademy.minidooraydgateway.domain.Milestone;
import com.nhnacademy.minidooraydgateway.domain.Project;
import com.nhnacademy.minidooraydgateway.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MilestoneService {

    private final ProjectServiceClient projectServiceClient;

    public List<MilestoneGetDto> getMilestonesByProjectId(Long projectId) {
        return projectServiceClient.getProjectMilestones(projectId).getBody();
    }


    public void createMilestone(Long projectId, MilestoneCreateRequestDto milestone) {
        projectServiceClient.createMilestone(projectId, milestone);
    }

    public void updateMilestone(Long projectId, MilestoneUpdateRequestDto milestone) {
        projectServiceClient.updateMilestone(projectId, milestone);
    }

    public void deleteMilestone(Long projectId, MilestoneGetByMilestoneIdRequestDto milestoneId) {
        projectServiceClient.deleteMilestone(projectId, milestoneId);
    }



}
