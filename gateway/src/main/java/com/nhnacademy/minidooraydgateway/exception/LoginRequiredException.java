package com.nhnacademy.minidooraydgateway.exception;

public class LoginRequiredException extends RuntimeException {
    public LoginRequiredException() {
        super("Login Required");
    }
}
