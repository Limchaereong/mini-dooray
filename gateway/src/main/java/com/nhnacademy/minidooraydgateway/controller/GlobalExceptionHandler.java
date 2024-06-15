package com.nhnacademy.minidooraydgateway.controller;

import com.nhnacademy.minidooraydgateway.exception.LoginRequiredException;
import com.nhnacademy.minidooraydgateway.exception.NonActiveMemberException;
import com.nhnacademy.minidooraydgateway.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NonActiveMemberException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleNonActiveMemberException(NonActiveMemberException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(LoginRequiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleLoginRequiredException(LoginRequiredException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }


    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleResponseStatusException(ResponseStatusException ex, Model model) {
        model.addAttribute("message", ex.getReason());
        return "error";
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleResponseStatusException(UserNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("message", "알 수 없는 오류가 발생했습니다.");
        return "error";
    }
}
