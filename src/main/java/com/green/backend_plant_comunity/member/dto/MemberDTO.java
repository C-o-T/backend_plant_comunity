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
}