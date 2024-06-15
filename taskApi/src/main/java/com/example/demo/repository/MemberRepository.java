package com.example.demo.repository;

import com.example.demo.entity.Member;
import com.example.demo.entity.MemberPk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, MemberPk> {
//    List<Member> findAllByMemberPk_ProjectId(Long projectId);

    List<Member> findAllByMemberPk_MemberId(Long memberId);

    Optional<Member> findFirstByMemberPk_ProjectIdAndMemberRole(Long projectId, Member.MemberRole role);

    Collection<Member> findAllByMemberPk_ProjectId(Long projectId);
}
