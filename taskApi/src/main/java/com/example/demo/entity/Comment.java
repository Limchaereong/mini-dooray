package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @NotNull
    private String commentContent;

    @ManyToOne(optional = false)
    @JoinColumn(name = "task_id")
    @JsonBackReference
    private Task task;

    public Comment(String commentContent, Task task) {
        this.commentContent = commentContent;
        this.task = task;
    }
}
