package com.green.backend_plant_comunity.member.mapper;

import com.green.backend_plant_comunity.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
  //회원가입
  public void joinMember(MemberDTO memberDTO);

  //회원아이디 중복검사
  public int checkId(String memId);

  //회원연락처 중복검사
  public int checkTell(String memTell);

  //사업자번호 중복검사
  public int checkBusinessNum(String memBusinessNum);

  //로그인하기
  public MemberDTO login (MemberDTO memberDTO);

  //아이디찾기
  public MemberDTO findId (MemberDTO memberDTO);

  //비밀번호찾기
  public MemberDTO findPw (MemberDTO memberDTO);
}
