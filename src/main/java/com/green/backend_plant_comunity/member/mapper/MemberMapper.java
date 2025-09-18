package com.green.backend_plant_comunity.member.mapper;

import com.green.backend_plant_comunity.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
  //회원가입
  public void joinMember(MemberDTO memberDTO);

  //회원아이디 중복검사
  public int checkId(String memId);

  //사업자번호 중복검사
  public int checkBusinessNum(String memBusinessNum);

  //로그인하기
  public MemberDTO login (MemberDTO memberDTO);

  //my page 화면 에서 회원정보 조회
  public MemberDTO getMemberDetail(String memId);


}
