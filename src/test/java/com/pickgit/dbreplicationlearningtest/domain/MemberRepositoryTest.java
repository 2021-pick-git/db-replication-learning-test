package com.pickgit.dbreplicationlearningtest.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
//@Import(DataSourceConfig.class)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void addMember() {
        Member member = new Member("pickgit", 29);
        memberRepository.save(member);
    }
}
