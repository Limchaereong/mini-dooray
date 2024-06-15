package com.example.demo.controller;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects/{projectId}/members")
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping
    public List<Long> getMembers(@PathVariable("projectId") Long projectId) {
        Collection<Member> members = memberRepository.findAllByMemberPk_ProjectId(projectId);
        return members.stream()
                .map(member -> member.getMemberPk().getMemberId())
                .collect(Collectors.toList());
    }


    @PostMapping
    public Member createMember(@PathVariable("projectId") Long projectId, @RequestBody Member member) {
        return memberRepository.save(member);
    }

}
