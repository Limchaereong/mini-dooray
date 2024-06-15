package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Member {
    public enum MemberRole {
        ADMIN,
        MEMBER;

        @JsonCreator
        public static Member.MemberRole jsonCreator(String str) {
            for (Member.MemberRole value : values()) {
                if (value.name().equalsIgnoreCase(str)) {
                    return value;
                }
            }
            return MEMBER;
        }
    }

    @EmbeddedId
    private MemberPk memberPk;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id")
    @MapsId("projectId")
    private Project project;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;
}
