package com.green.backend_plant_comunity.environment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActuatorLogDTO {
  private int logId;
  private String actName;
  private String raspNum;
  private String state;
  private String mode;
  private LocalDateTime eventTime;
}
