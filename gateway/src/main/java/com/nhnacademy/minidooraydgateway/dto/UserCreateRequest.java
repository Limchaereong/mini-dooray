package com.nhnacademy.minidooraydgateway.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserCreateRequest(@Email @NotBlank String email,
                                @NotBlank String password) {
}
