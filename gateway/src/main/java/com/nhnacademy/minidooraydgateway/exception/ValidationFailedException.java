package com.nhnacademy.minidooraydgateway.exception;

import org.springframework.validation.BindingResult;

public class ValidationFailedException extends RuntimeException {
    public ValidationFailedException(BindingResult bindingResult) {
    }
}
