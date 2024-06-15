package com.nhnacademy.minidooraydgateway.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
public class Project {
    private long id;
    private String name;
    private Status status;

    @Getter
    public enum Status {
        ACTIVE("할일"), DORMANT("진행중"), ENDED("완료");

        private final String description;

        Status(String description) {
            this.description = description;
        }

    }
}
