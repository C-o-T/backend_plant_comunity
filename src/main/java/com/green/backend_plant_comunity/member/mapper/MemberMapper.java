package com.green.backend_plant_comunity.member.mapper;

import com.green.backend_plant_comunity.member.dto.MemberDTO;
import com.green.backend_plant_comunity.member.dto.MemberProfileDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    // 회원가입
    int joinMember(MemberDTO memberDTO);

    // 아이디 중복검사
    int checkId(String memId);

    // 연락처 중복검사
    int checkTell(String memTell);

    // 사업자번호 중복검사
    int checkBusinessNum(String memBusinessNum);

    // 로그인
    MemberDTO login(MemberDTO memberDTO);

    // 아이디 찾기
    MemberDTO findId(MemberDTO memberDTO);

    // 비밀번호 찾기
    MemberDTO findPw(MemberDTO memberDTO);

    // 회원 상세정보 조회
    MemberDTO getMemberDetail(String memId);

    // 회원정보 수정
    int updateMember(MemberDTO memberDTO);

    // [관리자] 전체 활성 회원 목록 조회
    List<MemberDTO> selAllMembers();


    // [관리자] 삭제/탈퇴된 회원 목록 조회
    List<MemberDTO> selDeletedMembers();

    // 회원 검색 (쪽지용)
    List<MemberDTO> searchMembers(@Param("keyword") String keyword);

    // [공통] 회원 상태 확인
    String getMemberStatus(@Param("memId") String memId);

    // [관리자] 회원 상태 변경
    int updateMemberStatus(@Param("memId") String memId, @Param("status") String status);

    // [관리자] 회원 복구
    int restoreMember(@Param("memId") String memId);

    // [일반회원] 회원 탈퇴
    int withdrawMember(@Param("memId") String memId);

    // 프로필 이미지 등록
    int insertProfile(MemberProfileDTO memberProfileDTO);

    // 프로필 이미지 수정
    int updateProfile(MemberProfileDTO memberProfileDTO);

    // 회원의 프로필 이미지 조회
    MemberProfileDTO getProfileByMemId(@Param("memId") String memId);

    // 프로필 이미지 삭제
    int deleteProfile(@Param("memId") String memId);
}
