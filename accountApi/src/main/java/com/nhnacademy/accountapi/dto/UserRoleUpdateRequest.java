package com.nhnacademy.accountapi.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record UserRoleUpdateRequest(List<String> emails, String role) {
}
