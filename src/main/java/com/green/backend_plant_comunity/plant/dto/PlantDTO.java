package com.green.backend_plant_comunity.plant.dto;

import lombok.Data;

@Data
public class PlantDTO {
  private int herbNum;
  private String herbName;
  private float tempMin;
  private float tempMax;
  private float humidMin;
  private float humidMax;
  private float soilMin;
  private float soilMax;
  private int luxMin;
  private int luxMax;
  private String imgName;
}
