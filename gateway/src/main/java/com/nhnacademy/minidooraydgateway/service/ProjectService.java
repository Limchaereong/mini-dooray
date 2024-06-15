package com.nhnacademy.minidooraydgateway.service;

import com.nhnacademy.minidooraydgateway.client.ProjectServiceClient;
import com.nhnacademy.minidooraydgateway.domain.Project;
import com.nhnacademy.minidooraydgateway.dto.ProjectCreateDto;
import com.nhnacademy.minidooraydgateway.dto.ProjectGetDto;
import com.nhnacademy.minidooraydgateway.dto.ProjectStatusUpdateDto;
import com.nhnacademy.minidooraydgateway.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectServiceClient projectServiceClient;

//    public Page<Project> getAllProjectsByUserId(Pageable pageable, Long userId) {
//        ResponseEntity<Page<Project>> response = projectServiceClient.getAllProjectsByUserId(
//                userId,
//                pageable.getPageNumber(),
//                pageable.getPageSize(),
//                PaginationUtil.createSortParam(pageable)
//        );
//        return response.getBody();
//    }

    public ProjectGetDto getProjectById(Long projectId) {
        ResponseEntity<ProjectGetDto> response = projectServiceClient.getProjectById(projectId);
        return response.getBody();
    }

    public void createProject(ProjectCreateDto projectCreateDto) {
        projectServiceClient.createProject(projectCreateDto);
    }


    public Page<ProjectGetDto> getAllProjectsByUserId(Pageable pageable, long userId) {
        ResponseEntity<Page<ProjectGetDto>> response = projectServiceClient.getAllProjectsByUserId(
                userId,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                PaginationUtil.createSortParam(pageable));

        return response.getBody();
    }

    public void updateProjectStatus(Long projectId, ProjectStatusUpdateDto statusUpdateDto) {
        ResponseEntity<Void> response = projectServiceClient.updateProjectStatus(projectId, statusUpdateDto);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ResponseStatusException(response.getStatusCode(), "프로젝트 상태가 변경되지 않았습니다");
        }
    }
}
