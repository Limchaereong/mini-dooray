package com.nhnacademy.accountapi.dto;

import lombok.Builder;

@Builder
public record UserCreateRequest(String email, String password) {
}
