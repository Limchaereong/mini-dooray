package com.nhnacademy.minidooraydgateway.exception;

public class SessionNotFoundException extends RuntimeException {
    public SessionNotFoundException() {
        super("Invalid Session");
    }
}
