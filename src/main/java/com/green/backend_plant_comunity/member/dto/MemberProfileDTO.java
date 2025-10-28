package com.green.backend_plant_comunity.member.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberProfileDTO {
  private int profileNo;              // 프로필 고유번호 (PK)
  private String memId;                // 회원 ID (FK)
  private String profileImageUrl;      // 이미지 저장 경로
  private String profileImageName;     // 원본 파일명
  private LocalDateTime uploadDate;    // 업로드 날짜
}
