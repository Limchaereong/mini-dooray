package com.example.demo.controller;

import com.example.demo.entity.Member;
import com.example.demo.entity.MemberPk;
import com.example.demo.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class MemberControllerTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberController memberController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }
//
//    @Test
//    public void testGetMembers() throws Exception {
//        Member member1 = new Member(new MemberPk(1L, 1L), null, Member.MemberRole.MEMBER);
//        Member member2 = new Member(new MemberPk(2L, 1L), null, Member.MemberRole.ADMIN);
//
//        List<Member> members = Arrays.asList(member1, member2);
//        when(memberRepository.findAllByMemberPk_ProjectId(1L)).thenReturn(members);
//
//        mockMvc.perform(get("/projects/1/members")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].memberPk.memberId", is(1)))
//                .andExpect(jsonPath("$[0].memberPk.projectId", is(1)))
//                .andExpect(jsonPath("$[0].memberRole", is("MEMBER")))
//                .andExpect(jsonPath("$[1].memberPk.memberId", is(2)))
//                .andExpect(jsonPath("$[1].memberPk.projectId", is(1)))
//                .andExpect(jsonPath("$[1].memberRole", is("ADMIN")));
//
//        verify(memberRepository, times(1)).findAllByMemberPk_ProjectId(1L);
//    }
//
//    @Test
//    public void testCreateMember() throws Exception {
//        Member member = new Member(new MemberPk(1L, 1L), null, Member.MemberRole.MEMBER);
//
//        when(memberRepository.save(any(Member.class))).thenReturn(member);
//
//        mockMvc.perform(post("/projects/1/members")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"memberPk\":{\"memberId\":1,\"projectId\":1},\"memberRole\":\"MEMBER\"}"))
//                .andExpect(status().isCreated())  // 여기서 기대 상태 코드를 201로 변경
//                .andExpect(jsonPath("$.memberPk.memberId", is(1)))
//                .andExpect(jsonPath("$.memberPk.projectId", is(1)))
//                .andExpect(jsonPath("$.memberRole", is("MEMBER")));
//
//        verify(memberRepository, times(1)).save(any(Member.class));
//    }
}
