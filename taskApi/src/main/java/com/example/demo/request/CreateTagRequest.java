package com.example.demo.request;

import jakarta.validation.constraints.NotNull;

public class CreateTagRequest {
        @NotNull(message = "Tag name cannot be null")
        private String name;

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }
}
