package com.green.backend_plant_comunity.member.controller;

import com.green.backend_plant_comunity.member.dto.MemberDTO;
import com.green.backend_plant_comunity.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {
  private final MemberService memberService;

  //회원가입 버튼 누르면 실행할 api
  @PostMapping("")
  public void joinMember(@RequestBody MemberDTO memberDTO) {
    memberService.joinMember(memberDTO);
  }

  //회원아이디 중복검사 버튼 누르면 실행할 api
  @GetMapping("/id/{memId}")
  public int checkId(@PathVariable("memId") String memId) {
    return memberService.checkId(memId);
  }

  //회원연락처 중복검사 버튼 누르면 실행할 api
  @GetMapping("/tell/{memTell}")
  public int checkTell(@PathVariable("memTell") String memTell) {
    return memberService.checkTell(memTell);
  }

  //사업자번호 중복검사 버튼 누르면 실행할 api
  @GetMapping("/bn/{memBusinessNum}")
  public int checkBusinessNum(@PathVariable("memBusinessNum") String memBusinessNum) {
    return memberService.checkBusinessNum(memBusinessNum);
  }

  //로그인하기
  @GetMapping("/login")
  public MemberDTO login (MemberDTO memberDTO) {
    return memberService.login(memberDTO);
  }


  //아이디찾기
  @GetMapping("findId")
  public MemberDTO findId (MemberDTO memberDTO) {
    return memberService.findId(memberDTO);
  }

  //비밀번호찾기
  @GetMapping("findPw")
  public MemberDTO findPw (MemberDTO memberDTO) {
    return memberService.findPw(memberDTO);
  }

  //my page 화면 에서 회원정보 조회
  @GetMapping("/{memId}")
  public MemberDTO getMemberDetail(@PathVariable("memId") String memId){
    return memberService.getMemberDetail(memId);
  }

  // admin 페이지에서 단일 회원 삭제
  @DeleteMapping("/{memId}")
  public int deleteMember(@PathVariable("memId") String memId) {
    return memberService.deleteMemberByAdmin(memId);
  }
}
