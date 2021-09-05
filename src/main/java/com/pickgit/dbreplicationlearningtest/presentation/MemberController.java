package com.pickgit.dbreplicationlearningtest.presentation;

import com.pickgit.dbreplicationlearningtest.application.MemberService;
import com.pickgit.dbreplicationlearningtest.application.dto.MemberDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private MemberService memberService;

    public MemberController(
        MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberDto>> allMembers() {
        return ResponseEntity.ok(memberService.findAll());
    }

    @PostMapping("/members")
    public ResponseEntity<Void> addMember(@RequestBody MemberDto memberDto) {
        memberService.addMember(memberDto);
        return ResponseEntity.noContent().build();
    }

}
