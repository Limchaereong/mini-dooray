package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Task {
    public enum TaskStatus {
        TODO,
        IN_PROGRESS,
        DONE;

        @JsonCreator
        public static TaskStatus jsonCreator(String str) {
            for (TaskStatus value : values()) {
                if (value.name().equalsIgnoreCase(str)) {
                    return value;
                }
            }
            return TODO;
        }
    }

    public Task(String taskName, String taskDescription, TaskStatus taskStatus, Project project, Milestone milestone) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
        this.project = project;
        this.milestone = milestone;
    }

    @JsonProperty("taskId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @JsonProperty("name")
    @NotNull
    private String taskName;

    @JsonProperty("description")
    private String taskDescription;

    @JsonProperty("status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskStatus taskStatus;

//    @JsonProperty("projectId")
    @ManyToOne
    @NotNull
    @JoinColumn(name = "project_id")
    private Project project;

//    @JsonProperty("milestoneId")
    @OneToOne
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;


}
