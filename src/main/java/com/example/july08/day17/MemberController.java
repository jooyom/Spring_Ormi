package com.example.july08.day17;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/members")
//api를 요청하는 Member 응답 요청하는 컨트롤러구나 유추할 수 있어야함
//프리픽스?
public class MemberController {

    private List<Member> members = new ArrayList<>();
    private long nextId = 1;

    @GetMapping
    //모든 유저 조회 하는 로직
    public List<Member> getAllMembers(){
        return members;
    }

    @PostMapping
    public Member createMember(@RequestBody Member member){
        member.setId(nextId++);
        members.add(member);
        return member; //객체 반환 우리가 보는건 json이기 때문에 타입이 다르다.
        //자바에서 자동으로 잭슨이(fasterxml.jackson.core:) 제이슨으로 변형해서 쏴줌
    }

    /*@GetMapping("/{id}")
    public Member getMemberById(@PathVariable("id") Long id){
        Member member1 = members.stream()
                .filter(member -> member.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());
    }*/

    //이건 404로 반환하는 코드임
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable("id") Long id) {
        Member member1 = members.stream()
                .filter(member -> member.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());
        MemberDTO memberDTO = new MemberDTO(member1.getEmail());
        return ResponseEntity.status(404).body(member1);
    }

        @PutMapping("/{id}")
    //put 업데이트(전체 수정) patch
    public Member updateMember(@PathVariable("id") Long id, @RequestBody Member updateMember){
        Member member = members.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("멤버를 찾지못하였습니다."));

        member.setName(updateMember.getName());
        member.setEmail(updateMember.getEmail());
        return member;
    }

    @DeleteMapping("/{id}")
    public void delteMember(@PathVariable("id") Long id){
        members.removeIf(member->member.getId() == id);
    }
}
