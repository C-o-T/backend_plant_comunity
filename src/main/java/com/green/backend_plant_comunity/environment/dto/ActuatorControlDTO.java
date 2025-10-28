package com.green.backend_plant_comunity.environment.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ActuatorControlDTO {
  private int controlId;           // 제어 ID (PK)
  private String actName;          // 액추에이터 이름 (PUMP, FAN, LED)
  private String raspNum;          // 라즈베리파이 시리얼 번호
  private String command;          // 제어 명령 (ON, OFF, AUTO)
  private String mode;             // 제어 모드 (AUTO, MANUAL)
  private LocalDateTime createdTime; // 생성 시간
  private boolean processed;       // 처리 여부
}
