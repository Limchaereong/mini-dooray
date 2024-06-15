package com.nhnacademy.minidooraydgateway.dto;

import com.nhnacademy.minidooraydgateway.domain.Project;
import lombok.Builder;

import java.util.List;

@Builder
public record ProjectCreateRequest(String name, Project.Status status, List<String> memberEmails) {
}
