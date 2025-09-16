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

  //사업자번호 중복검사 버튼 누르면 실행할 api
  @GetMapping("/bn/{memBusinessNum}")
  public int checkBusinessNum(@PathVariable("memBusinessNum") String memBusinessNum) {
    return memberService.checkBusinessNum(memBusinessNum);
  }
}
