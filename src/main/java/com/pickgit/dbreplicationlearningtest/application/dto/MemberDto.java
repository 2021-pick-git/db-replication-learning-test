package com.pickgit.dbreplicationlearningtest.application.dto;

import com.pickgit.dbreplicationlearningtest.domain.Member;

public class MemberDto {

    private String name;

    private Integer age;

    private MemberDto() {
    }

    public MemberDto(Member member) {
        this(member.getName(), member.getAge());
    }

    public MemberDto(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
