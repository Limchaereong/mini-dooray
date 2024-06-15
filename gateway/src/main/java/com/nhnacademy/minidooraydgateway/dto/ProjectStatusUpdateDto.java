package com.nhnacademy.minidooraydgateway.dto;

import com.nhnacademy.minidooraydgateway.domain.Project;
import lombok.Builder;

@Builder
public record ProjectStatusUpdateDto(Project.Status status) {
}
