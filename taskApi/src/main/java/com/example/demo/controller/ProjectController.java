package com.example.demo.controller;

import com.example.demo.dto.ProjectCreateDto;
import com.example.demo.dto.ProjectGetDto;
import com.example.demo.dto.ProjectStatusUpdateDto;
import com.example.demo.entity.Project;
import com.example.demo.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    // 특정 유저의 프로젝트 목록 조회
    @GetMapping
    public ResponseEntity<Page<ProjectGetDto>> getAllProjectsByUserId(@RequestParam("userId") Long userId,
                                                                      @RequestParam("page") int page,
                                                                      @RequestParam("size") int size,
                                                                      @RequestParam(required = false) String sort) {
        Pageable pageable;
        if (sort != null) {
            pageable = PageRequest.of(page, size, Sort.by(sort));
        } else {
            pageable = PageRequest.of(page, size);
        }
        Page<ProjectGetDto> projects = projectService.getAllProjectsByUserId(userId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(projects);
    }

    // 프로젝트 생성
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody ProjectCreateDto projectCreateDto) {
        Project project = projectService.createProject(projectCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }


    // 프로젝트 상태 변경
    @PutMapping("/{projectId}/status")
    public ResponseEntity<Void> updateProjectStatus(@PathVariable Long projectId, @RequestBody ProjectStatusUpdateDto projectStatusUpdateDto) {
        projectService.updateProjectStatus(projectId, projectStatusUpdateDto.status());
        return ResponseEntity.ok().build();
    }

    // Project
    // 특정 프로젝트 조회
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectGetDto> getProjectById(@PathVariable("projectId") Long projectId) {
        ProjectGetDto projectGetDto = projectService.getProjectByProjectId(projectId);
        return ResponseEntity.ok(projectGetDto);
    }


}
