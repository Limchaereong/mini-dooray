package com.example.demo.request;

import com.example.demo.entity.Project;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;


public record CreateProjectRequest(@NotNull String projectName,
                                   @Enumerated(EnumType.STRING)
                                   @NotNull Project.ProjectStatus projectStatus) {

}
