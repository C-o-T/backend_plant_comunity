package com.green.backend_plant_comunity.member.controller;

import com.green.backend_plant_comunity.member.dto.MemberDTO;
import com.green.backend_plant_comunity.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  // [관리자] 활성 회원 목록 조회
  @GetMapping("/admin")
  public ResponseEntity<List<MemberDTO>> getAllMembers() {
    List<MemberDTO> members = memberService.getAllMembers();
    return ResponseEntity.ok(members);
  }

  // [관리자] 삭제된 회원 목록 조회
  @GetMapping("/admin/deleted")
  public ResponseEntity<List<MemberDTO>> getDeletedMembers() {
    List<MemberDTO> deletedMembers = memberService.getDeletedMembers();
    return ResponseEntity.ok(deletedMembers);
  }

  // [관리자] 회원 논리적 삭제 (상태 변경)
  @PutMapping("/admin/{memId}/delete")
  public ResponseEntity<Map<String, Object>> deleteMemberByAdmin(@PathVariable String memId) {
    Map<String, Object> response = new HashMap<>();
    
    try {
      int result = memberService.deleteMemberByAdmin(memId);
      
      if (result > 0) {
        response.put("success", true);
        response.put("message", "회원이 성공적으로 삭제 처리되었습니다.");
      } else {
        response.put("success", false);
        response.put("message", "회원 삭제 처리에 실패했습니다.");
      }
    } catch (Exception e) {
      response.put("success", false);
      response.put("message", "오류가 발생했습니다: " + e.getMessage());
    }
    
    return ResponseEntity.ok(response);
  }

  // [관리자] 회원 복구
  @PutMapping("/admin/{memId}/restore")
  public ResponseEntity<Map<String, Object>> restoreMemberByAdmin(@PathVariable String memId) {
    Map<String, Object> response = new HashMap<>();
    
    try {
      int result = memberService.restoreMemberByAdmin(memId);
      
      if (result > 0) {
        response.put("success", true);
        response.put("message", "회원이 성공적으로 복구되었습니다.");
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

  // [관리자] 회원 정지
  @PutMapping("/admin/{memId}/suspend")
  public ResponseEntity<Map<String, Object>> suspendMemberByAdmin(@PathVariable String memId) {
    Map<String, Object> response = new HashMap<>();
    
    try {
      int result = memberService.suspendMemberByAdmin(memId);
      
      if (result > 0) {
        response.put("success", true);
        response.put("message", "회원이 성공적으로 정지 처리되었습니다.");
      } else {
        response.put("success", false);
        response.put("message", "회원 정지 처리에 실패했습니다.");
      }
    } catch (Exception e) {
      response.put("success", false);
      response.put("message", "오류가 발생했습니다: " + e.getMessage());
    }
    
    return ResponseEntity.ok(response);
  }

  // 회원 상태 확인
  @GetMapping("/status/{memId}")
  public ResponseEntity<Map<String, Object>> getMemberStatus(@PathVariable String memId) {
    Map<String, Object> response = new HashMap<>();
    
    try {
      String status = memberService.getMemberStatus(memId);
      boolean isActive = "ACTIVE".equals(status);
      
      response.put("success", true);
      response.put("status", status);
      response.put("isActive", isActive);
    } catch (Exception e) {
      response.put("success", false);
      response.put("message", "오류가 발생했습니다: " + e.getMessage());
    }
    
    return ResponseEntity.ok(response);
  }

  // 회원 검색 (쪽지 보낼 때)
  @GetMapping("/search")
  public ResponseEntity<List<MemberDTO>> searchMembers(@RequestParam String keyword) {
    try {
      List<MemberDTO> members = memberService.searchMembers(keyword);
      return ResponseEntity.ok(members);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
