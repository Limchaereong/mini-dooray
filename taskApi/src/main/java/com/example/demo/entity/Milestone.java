package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.ZonedDateTime;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Milestone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long milestoneId;

    @NotNull
    @Setter
    private String milestoneName;

    @Setter
    private ZonedDateTime milestoneStartDate;
    @Setter
    private ZonedDateTime milestoneEndDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "projectId")
    private Project project;

    public Milestone(String milestoneName, ZonedDateTime milestoneStartDate, ZonedDateTime milestoneEndDate,Project project) {
        this.milestoneName = milestoneName;
        this.milestoneStartDate = milestoneStartDate;
        this.milestoneEndDate = milestoneEndDate;
        this.project = project;
    }

}
