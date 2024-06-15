package com.nhnacademy.minidooraydgateway.exception;

public class SecurityContextNotFoundException extends RuntimeException{
    public SecurityContextNotFoundException() {
        super("Security Context not found");
    }
}
