package com.green.backend_plant_comunity.member.service;

import com.green.backend_plant_comunity.board.mapper.BoardMapper;
import com.green.backend_plant_comunity.member.dto.MemberDTO;
import com.green.backend_plant_comunity.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberMapper memberMapper;

  //회원가입
  public void joinMember(MemberDTO memberDTO){
    memberMapper.joinMember(memberDTO);
  }

  //회원아이디 중복검사
  public int checkId(String memId){

    return memberMapper.checkId(memId);
  }

  //회원아이디 중복검사
  public int checkTell(String memTell){

    return memberMapper.checkTell(memTell);
  }

  //사업자번호 중복검사
  public int checkBusinessNum(String memBusinessNum){
    return memberMapper.checkBusinessNum(memBusinessNum);
  }

  //로그인하기
  public MemberDTO login (MemberDTO memberDTO) {
    return memberMapper.login(memberDTO);
  }


  //아이디찾기
  public MemberDTO findId (MemberDTO memberDTO) {
    return memberMapper.findId(memberDTO);
  }

  //비밀번호찾기
  public MemberDTO findPw (MemberDTO memberDTO) {
    return memberMapper.findPw(memberDTO);
  }

  //my page 화면 에서 회원정보 조회
  public MemberDTO getMemberDetail(String memId){
    return memberMapper.getMemberDetail(memId);
  }


}
