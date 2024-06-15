package com.nhnacademy.minidooraydgateway.dto;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record UserRoleUpdateRequest(List<String> emails, String role) {
}
