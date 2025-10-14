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

    // 회원가입
    public int joinMember(MemberDTO memberDTO) {
        return memberMapper.joinMember(memberDTO);
    }

    // 아이디 중복검사
    public int checkId(String memId) {
        return memberMapper.checkId(memId);
    }

    // 연락처 중복검사
    public int checkTell(String memTell) {
        return memberMapper.checkTell(memTell);
    }

    // 사업자번호 중복검사
    public int checkBusinessNum(String memBusinessNum) {
        return memberMapper.checkBusinessNum(memBusinessNum);
    }

    // 로그인
    public MemberDTO login(MemberDTO memberDTO) {
        return memberMapper.login(memberDTO);
    }

    // 아이디 찾기
    public MemberDTO findId(MemberDTO memberDTO) {
        return memberMapper.findId(memberDTO);
    }

    // 비밀번호 찾기
    public MemberDTO findPw(MemberDTO memberDTO) {
        return memberMapper.findPw(memberDTO);
    }

    // 회원 상세정보 조회
    public MemberDTO getMemberDetail(String memId) {
        return memberMapper.getMemberDetail(memId);
    }

    // 회원정보 수정
    public int updateMember(MemberDTO memberDTO) {
        return memberMapper.updateMember(memberDTO);
    }

    // [관리자] 전체 활성 회원 목록 조회
    public List<MemberDTO> getAllMembers() {
        return memberMapper.selAllMembers();
    }

    // [관리자] 삭제/탈퇴된 회원 목록 조회
    public List<MemberDTO> getDeletedMembers() {
        return memberMapper.selDeletedMembers();
    }

    // 회원 검색
    public List<MemberDTO> searchMembers(String keyword) {
        return memberMapper.searchMembers(keyword);
    }

    // [공통] 회원 상태 확인
    public String getMemberStatus(String memId) {
        return memberMapper.getMemberStatus(memId);
    }

    // [관리자] 회원 상태 변경
    public int updateMemberStatus(String memId, String status) {
        return memberMapper.updateMemberStatus(memId, status);
    }

    // [관리자] 회원 복구
    public int restoreMember(String memId) {
        return memberMapper.restoreMember(memId);
    }

    // [일반회원] 회원 탈퇴
    public int withdrawMember(String memId) {
        return memberMapper.withdrawMember(memId);
    }
}
