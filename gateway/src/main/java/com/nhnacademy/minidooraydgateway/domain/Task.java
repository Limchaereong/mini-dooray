package com.nhnacademy.minidooraydgateway.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
public class Task {
    @Getter
    public enum TaskStatus {
        TODO("할일"), IN_PROGRESS("진행중"), DONE("완료");

        private final String taskDescription;

        TaskStatus(String taskDescription) {
            this.taskDescription = taskDescription;
        }

    }

    private String name;

    private String description;

    @NotBlank
    private TaskStatus status;

    private Long taskId;

    private long projectId;

    private long milestoneId;

}
