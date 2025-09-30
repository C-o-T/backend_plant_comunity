package com.green.backend_plant_comunity.environment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActuatorLogDTO {
  private int logId;
  private String actName;
  private String raspNum;
  private String state;
  private LocalDateTime eventTime;
}
