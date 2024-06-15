package com.nhnacademy.minidooraydgateway.controller;

import com.nhnacademy.minidooraydgateway.dto.UserCreateRequest;
import com.nhnacademy.minidooraydgateway.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("userDto", UserCreateRequest.builder().build());
        return "account/signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@Valid UserCreateRequest userCreateRequest, BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getField)
                    .collect(Collectors.joining(", ")) + "이(가) 잘못되었습니다.";
            model.addAttribute("error", errorMessage);
            return "account/signup";
        }

        try {
            userService.saveUser(userCreateRequest);
            return "home";
        } catch (ResponseStatusException ex) {
            model.addAttribute("error", ex.getReason());
            return "account/signup";
        }
    }
}
