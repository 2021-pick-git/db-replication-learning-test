package com.pickgit.dbreplicationlearningtest.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("db")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("Master DB에 데이터를 추가하면 slave DB에도 반영된다.")
    @Test
    void addMember_Success() {
        // given
        Member member = new Member("pickgit", 29);
        memberRepository.save(member);
    }

    @DisplayName("Slave DB에서 데이터를 조회한다 - 여러번 조회시 slave db를 번갈아 조회한다.")
    @Test
    void findMember_Success() {
        // given
        Member member = memberRepository.save( new Member("pickgit", 29));

        // when
        Member findMember1 = memberRepository.findById(member.getId())
            .orElseThrow();
        Member findMember2 = memberRepository.findById(member.getId())
            .orElseThrow();
        Member findMember3 = memberRepository.findById(member.getId())
            .orElseThrow();
        Member findMember4 = memberRepository.findById(member.getId())
            .orElseThrow();

        // then
        assertThat(findMember1.getId()).isNotNull();
        assertThat(findMember1.getName()).isEqualTo(member.getName());
        assertThat(findMember1.getAge()).isEqualTo(member.getAge());
    }
}
