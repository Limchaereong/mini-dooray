package com.example.demo.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateTagNameRequest {

    @NotNull(message = "Tag name cannot be null")
    private String tagName;

}
