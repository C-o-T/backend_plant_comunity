package com.green.backend_plant_comunity.member.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDTO {
  private String memId;
  private String memPw;
  private String memName;
  private String memAddr;
  private String memDetailAddr;
  private String memTell;
  private String memEmail;
  private String memGrade;
  private LocalDateTime joinDate;
  private String memBusinessNum;
  private String memBusinessName;
  private String memStatus;  // ACTIVE, WITHDRAWN(회원탈퇴), DELETED(관리자삭제), SUSPENDED(정지)
  private String profileImageUrl;  // 프로필 이미지 URL
}