package com.green.backend_plant_comunity.member.service;

import com.green.backend_plant_comunity.member.dto.MemberDTO;
import com.green.backend_plant_comunity.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberMapper memberMapper;

  //회원가입
  public void joinMember(MemberDTO memberDTO){
    // 기본값 설정
    if (memberDTO.getMemStatus() == null) {
      memberDTO.setMemStatus("ACTIVE");
    }
    memberMapper.joinMember(memberDTO);
  }

  //회원아이디 중복검사 (활성 회원만)
  public int checkId(String memId){
    return memberMapper.checkId(memId);
  }

  //회원연락처 중복검사 (활성 회원만)
  public int checkTell(String memTell){
    return memberMapper.checkTell(memTell);
  }

  //사업자번호 중복검사 (활성 회원만)
  public int checkBusinessNum(String memBusinessNum){
    return memberMapper.checkBusinessNum(memBusinessNum);
  }

  //로그인하기 (활성 회원만)
  public MemberDTO login (MemberDTO memberDTO) {
    MemberDTO loginMember = memberMapper.login(memberDTO);
    
    // 삭제된 회원이 로그인 시도하는 경우 체크
    if (loginMember != null && !"ACTIVE".equals(loginMember.getMemStatus())) {
      return null; // 로그인 불가
    }
    
    return loginMember;
  }

  //아이디찾기 (활성 회원만)
  public MemberDTO findId (MemberDTO memberDTO) {
    return memberMapper.findId(memberDTO);
  }

  //비밀번호찾기 (활성 회원만)
  public MemberDTO findPw (MemberDTO memberDTO) {
    return memberMapper.findPw(memberDTO);
  }

  //my page 화면에서 회원정보 조회
  public MemberDTO getMemberDetail(String memId){
    return memberMapper.getMemberDetail(memId);
  }

  // [관리자] 활성 회원 목록 조회
  public List<MemberDTO> getAllMembers() {
    return memberMapper.selAllMembers();
  }

  // [관리자] 삭제된 회원 목록 조회
  public List<MemberDTO> getDeletedMembers() {
    return memberMapper.selDeletedMembers();
  }

  // 회원 상태 확인
  public String getMemberStatus(String memId) {
    return memberMapper.getMemberStatus(memId);
  }

  // [관리자] 회원 논리적 삭제 (상태를 DELETED로 변경)
  public int deleteMemberByAdmin(String memId) {
    return memberMapper.updateMemberStatus(memId, "DELETED");
  }

  // [관리자] 회원 복구 (DELETED -> ACTIVE)
  public int restoreMemberByAdmin(String memId) {
    return memberMapper.restoreMember(memId);
  }

  // [관리자] 회원 정지 (ACTIVE -> SUSPENDED)
  public int suspendMemberByAdmin(String memId) {
    return memberMapper.updateMemberStatus(memId, "SUSPENDED");
  }

  // 회원이 활성 상태인지 확인
  public boolean isActiveMember(String memId) {
    String status = getMemberStatus(memId);
    return "ACTIVE".equals(status);
  }

  // 회원 검색 (쪽지 보낼 때)
  public List<MemberDTO> searchMembers(String keyword) {
    return memberMapper.searchMembers(keyword);
  }
}
