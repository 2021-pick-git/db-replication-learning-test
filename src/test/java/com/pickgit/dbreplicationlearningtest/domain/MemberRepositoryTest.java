package com.pickgit.dbreplicationlearningtest.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
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

    @DisplayName("Slave DB에서 데이터를 조회한다.")
    @Test
    void findMember_Success() {
        // given
        Member findMember = memberRepository.findById(1L)
            .orElseThrow();

        // when
        // then
        assertThat(findMember.getId()).isNotNull();
    }
}
