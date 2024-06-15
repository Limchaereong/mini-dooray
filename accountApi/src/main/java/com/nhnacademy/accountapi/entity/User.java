package com.nhnacademy.accountapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user") // 테이블 이름을 명시적으로 설정
public class User {

    public enum Status {
        ACTIVE("가입"),
        DORMANT("휴면"),
        ENDED("탈퇴");

        private final String description;

        Status(String description) {
            this.description = description;
        }
    }

    public enum Role {
        MEMBER,
        PROJECT_MEMBER,
        PROJECT_ADMIN;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
