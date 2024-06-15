package com.example.demo.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void testNoArgsConstructor() {
        // Given
        Member member = new Member();

        // Then
        assertNotNull(member);
        assertNull(member.getMemberPk());
        assertNull(member.getProject());
        assertNull(member.getMemberRole());
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        MemberPk memberPk = new MemberPk(1L, 2L);
        Project project = new Project();
        Member.MemberRole memberRole = Member.MemberRole.ADMIN;

        // When
        Member member = new Member(memberPk, project, memberRole);

        // Then
        assertNotNull(member);
        assertEquals(memberPk, member.getMemberPk());
        assertEquals(project, member.getProject());
        assertEquals(memberRole, member.getMemberRole());
    }

    @Test
    void testMemberRoleEnum() {
        // Given
        String adminRole = "ADMIN";
        String memberRole = "MEMBER";
        String unknownRole = "UNKNOWN";

        // When & Then
        assertEquals(Member.MemberRole.ADMIN, Member.MemberRole.jsonCreator(adminRole));
        assertEquals(Member.MemberRole.MEMBER, Member.MemberRole.jsonCreator(memberRole));
        assertEquals(Member.MemberRole.MEMBER, Member.MemberRole.jsonCreator(unknownRole)); // Default case
    }

    @Test
    void testGetterMethods() {
        // Given
        MemberPk memberPk = new MemberPk(1L, 2L);
        Project project = new Project();
        Member.MemberRole memberRole = Member.MemberRole.ADMIN;

        // When
        Member member = new Member(memberPk, project, memberRole);

        // Then
        assertEquals(memberPk, member.getMemberPk());
        assertEquals(project, member.getProject());
        assertEquals(memberRole, member.getMemberRole());
    }
}
