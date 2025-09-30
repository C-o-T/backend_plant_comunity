package com.green.backend_plant_comunity.member.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Builder
@Data
public class MemberRes {
  private String memId;
  private String memName;
  private String memTell;
  private String memAddr;
  private String memBusinessNum;
  private LocalDateTime regDate;
}