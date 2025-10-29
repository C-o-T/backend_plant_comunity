package com.green.backend_plant_comunity.member.service;

import com.green.backend_plant_comunity.member.dto.MemberDTO;
import com.green.backend_plant_comunity.member.dto.MemberProfileDTO;
import com.green.backend_plant_comunity.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Transactional(readOnly = true)
    public MemberDTO login(MemberDTO memberDTO) {
        MemberDTO member = memberMapper.login(memberDTO);

        // 로그인 성공 시 프로필 이미지 정보도 함께 가져오기
        if (member != null) {
            MemberProfileDTO profile = memberMapper.getProfileByMemId(member.getMemId());
            if (profile != null) {
                member.setProfileImageUrl(profile.getProfileImageUrl());
            }
        }

        return member;
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
    @Transactional(readOnly = true)
    public List<MemberDTO> getAllMembers() {
        List<MemberDTO> members = memberMapper.selAllMembers();

        // 각 회원의 프로필 이미지 정보도 함께 가져오기
        for (MemberDTO member : members) {
            MemberProfileDTO profile = memberMapper.getProfileByMemId(member.getMemId());
            if (profile != null) {
                member.setProfileImageUrl(profile.getProfileImageUrl());
            }
        }

        return members;
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

    @Value("${file.upload-dir:D:/01-STUDY/dev/team/upload/profile/}")
    private String uploadDir;

    // 프로필 이미지 업로드 및 저장
    @Transactional
    public Map<String, String> uploadProfileImage(MultipartFile file, String memId) throws IOException {
        // 프로필 전용 디렉토리 생성
        String profileDir = uploadDir + "profile/";
        File dir = new File(profileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 파일명 생성 (중복 방지)
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = memId + "_" + System.currentTimeMillis() + extension;

        // 파일 저장
        File dest = new File(profileDir + newFilename);
        System.out.println("파일 저장 경로: " + dest.getAbsolutePath());
        file.transferTo(dest);
        System.out.println("파일 저장 완료: " + dest.exists());

        // 파일 URL 생성
        String fileUrl = "/upload/profile/" + newFilename;

        // DB에 저장
        MemberProfileDTO profileDTO = new MemberProfileDTO();
        profileDTO.setMemId(memId);
        profileDTO.setProfileImageUrl(fileUrl);
        profileDTO.setProfileImageName(originalFilename);

        // 기존 프로필 확인
        MemberProfileDTO existingProfile = memberMapper.getProfileByMemId(memId);

        if (existingProfile != null) {
            // 기존 프로필이 있으면 업데이트
            // 기존 파일 삭제
            deleteOldProfileImage(existingProfile.getProfileImageUrl());
            memberMapper.updateProfile(profileDTO);
        } else {
            // 없으면 새로 등록
            memberMapper.insertProfile(profileDTO);
        }

        // 결과 반환
        Map<String, String> result = new HashMap<>();
        result.put("profileImageUrl", fileUrl);
        result.put("message", "프로필 이미지가 성공적으로 업로드되었습니다.");

        return result;
    }

    // 기존 프로필 이미지 파일 삭제
    private void deleteOldProfileImage(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            String filename = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            File oldFile = new File(uploadDir + "profile/" + filename);
            if (oldFile.exists()) {
                oldFile.delete();
            }
        }
    }

    // 회원의 프로필 이미지 조회
    public MemberProfileDTO getProfileByMemId(String memId) {
        return memberMapper.getProfileByMemId(memId);
    }
}
