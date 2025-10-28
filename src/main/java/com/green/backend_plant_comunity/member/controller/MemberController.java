package com.green.backend_plant_comunity.member.controller;

import com.green.backend_plant_comunity.member.dto.MemberDTO;
import com.green.backend_plant_comunity.member.dto.MemberProfileDTO;
import com.green.backend_plant_comunity.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 회원가입
    @PostMapping
    public int joinMember(@RequestBody MemberDTO memberDTO) {
        return memberService.joinMember(memberDTO);
    }

    // 아이디 중복검사
    @GetMapping("/checkId/{memId}")
    public int checkId(@PathVariable String memId) {
        return memberService.checkId(memId);
    }

    // 연락처 중복검사
    @GetMapping("/checkTell/{memTell}")
    public int checkTell(@PathVariable String memTell) {
        return memberService.checkTell(memTell);
    }

    // 사업자번호 중복검사
    @GetMapping("/checkBusinessNum/{memBusinessNum}")
    public int checkBusinessNum(@PathVariable String memBusinessNum) {
        return memberService.checkBusinessNum(memBusinessNum);
    }

    // 로그인
    @GetMapping("/login")
    public MemberDTO login(MemberDTO memberDTO) {
        System.out.println("========================================");
        System.out.println("로그인 API 호출됨!!!");
        System.out.println("요청 ID: " + memberDTO.getMemId());
        System.out.println("========================================");
        MemberDTO result = memberService.login(memberDTO);
        System.out.println("로그인 결과 프로필URL: " + (result != null ? result.getProfileImageUrl() : "null"));
        System.out.println("========================================");
        return result;
    }

    // 아이디 찾기
    @GetMapping("/findId")
    public MemberDTO findId(MemberDTO memberDTO) {
        return memberService.findId(memberDTO);
    }

    // 비밀번호 찾기
    @GetMapping("/findPw")
    public MemberDTO findPw(MemberDTO memberDTO) {
        return memberService.findPw(memberDTO);
    }

    // 회원 상세정보 조회
    @GetMapping("/{memId}")
    public MemberDTO getMemberDetail(@PathVariable String memId) {
        return memberService.getMemberDetail(memId);
    }

    // 회원정보 수정
    @PutMapping("/{memId}")
    public MemberDTO updateMember(@PathVariable String memId, @RequestBody MemberDTO memberDTO) {
        memberService.updateMember(memberDTO);
        return memberService.getMemberDetail(memId);
    }

    // [관리자] 전체 활성 회원 목록 조회
    @GetMapping("/admin")
    public List<MemberDTO> getAllMembers() {
        return memberService.getAllMembers();
    }

    // [관리자] 삭제/탈퇴된 회원 목록 조회
    @GetMapping("/admin/deleted")
    public List<MemberDTO> getDeletedMembers() {
        return memberService.getDeletedMembers();
    }

    // 회원 검색
    @GetMapping("/search")
    public List<MemberDTO> searchMembers(@RequestParam String keyword) {
        return memberService.searchMembers(keyword);
    }

    // [공통] 회원 상태 확인
    @GetMapping("/status/{memId}")
    public ResponseEntity<Map<String, Object>> getMemberStatus(@PathVariable String memId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String status = memberService.getMemberStatus(memId);
            
            if (status != null) {
                response.put("success", true);
                response.put("status", status);
            } else {
                response.put("success", false);
                response.put("message", "존재하지 않는 회원입니다.");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "오류가 발생했습니다.");
        }
        
        return ResponseEntity.ok(response);
    }

    // [관리자] 회원 논리적 삭제
    @PutMapping("/admin/{memId}/delete")
    public ResponseEntity<Map<String, Object>> deleteMemberByAdmin(@PathVariable String memId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            int result = memberService.updateMemberStatus(memId, "DELETED");
            
            if (result > 0) {
                response.put("success", true);
                response.put("message", "회원이 삭제되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "회원 삭제에 실패했습니다.");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "오류가 발생했습니다: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    // [관리자] 회원 복구
    @PutMapping("/admin/{memId}/restore")
    public ResponseEntity<Map<String, Object>> restoreMember(@PathVariable String memId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            int result = memberService.restoreMember(memId);
            
            if (result > 0) {
                response.put("success", true);
                response.put("message", "회원이 복구되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "회원 복구에 실패했습니다.");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "오류가 발생했습니다: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    // [일반회원] 회원 탈퇴
    @PutMapping("/{memId}/withdraw")
    public ResponseEntity<Map<String, Object>> withdrawMember(@PathVariable String memId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            int result = memberService.withdrawMember(memId);
            
            if (result > 0) {
                response.put("success", true);
                response.put("message", "회원 탈퇴가 완료되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "회원 탈퇴에 실패했습니다.");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "오류가 발생했습니다: " + e.getMessage());
        }
        
        return ResponseEntity.ok(response);
    }

    // 프로필 이미지 업로드
    @PostMapping("/profile-image")
    public ResponseEntity<?> uploadProfileImage(
            @RequestParam("profileImage") MultipartFile file,
            @RequestParam("memId") String memId) {

        Map<String, Object> response = new HashMap<>();

        try {
            // 파일이 비어있는지 확인
            if (file.isEmpty()) {
                response.put("success", false);
                response.put("message", "파일이 비어있습니다.");
                return ResponseEntity.badRequest().body(response);
            }

            // 파일 확장자 검증 (jpg, jpeg, png만 허용)
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();

            if (!extension.equals(".jpg") && !extension.equals(".jpeg") && !extension.equals(".png")) {
                response.put("success", false);
                response.put("message", "JPG, JPEG, PNG 파일만 업로드 가능합니다.");
                return ResponseEntity.badRequest().body(response);
            }

            // 파일 크기 검증 (10MB 이하)
            if (file.getSize() > 10 * 1024 * 1024) {
                response.put("success", false);
                response.put("message", "파일 크기는 10MB를 초과할 수 없습니다.");
                return ResponseEntity.badRequest().body(response);
            }

            // 파일 업로드 및 DB 저장
            Map<String, String> result = memberService.uploadProfileImage(file, memId);

            response.put("success", true);
            response.put("profileImageUrl", result.get("profileImageUrl"));
            response.put("message", result.get("message"));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace(); // 콘솔에 상세 에러 출력
            response.put("success", false);
            response.put("message", "프로필 이미지 업로드 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // 프로필 이미지 조회
    @GetMapping("/profile-image/{memId}")
    public ResponseEntity<?> getProfileImage(@PathVariable String memId) {
        Map<String, Object> response = new HashMap<>();

        try {
            MemberProfileDTO profile = memberService.getProfileByMemId(memId);

            if (profile != null) {
                response.put("success", true);
                response.put("profile", profile);
            } else {
                response.put("success", false);
                response.put("message", "프로필 이미지가 없습니다.");
            }

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "프로필 이미지 조회 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
