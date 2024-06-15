package com.nhnacademy.minidooraydgateway.service;

import com.nhnacademy.minidooraydgateway.client.ProjectServiceClient;
import com.nhnacademy.minidooraydgateway.domain.User;
import com.nhnacademy.minidooraydgateway.dto.ProjectMemberDto;
import com.nhnacademy.minidooraydgateway.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectMemberService {

    private final ProjectServiceClient projectServiceClient;

    public void addMemberToProject(Long projectId, List<Long> memberIds) {
        projectServiceClient.addMemberToProject(projectId, ProjectMemberDto.builder().memberIds(memberIds).build());
    }

    public List<Long> getAllProjectMembers(Long projectId) {
        ResponseEntity<List<Long>> response = projectServiceClient.getProjectMembers(projectId);
        return response.getBody();
    }

}
