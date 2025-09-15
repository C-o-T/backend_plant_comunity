package com.green.backend_plant_comunity.environment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EnvironmentDTO {
  private int envNum;  //PK
  private LocalDateTime sensorTime;  //센서시간
  private float temperature;  //온도
  private float humidity;  //습도
  private int illuminance;  //조도
  private float soilMoisture; //토양습도

}
