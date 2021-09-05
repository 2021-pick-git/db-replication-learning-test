package com.pickgit.dbreplicationlearningtest.application;

import static java.util.stream.Collectors.toList;

import com.pickgit.dbreplicationlearningtest.application.dto.MemberDto;
import com.pickgit.dbreplicationlearningtest.domain.Member;
import com.pickgit.dbreplicationlearningtest.domain.MemberRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberDto> findAll() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
            .map(MemberDto::new)
            .collect(toList());
    }

    @Transactional
    public void addMember(MemberDto memberDto) {
        Member member = new Member(memberDto.getName(), memberDto.getAge());
        memberRepository.save(member);
    }
}
