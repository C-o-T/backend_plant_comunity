package com.green.backend_plant_comunity.environment.dto;

import lombok.Data;

@Data
public class StandardsDTO {
  private int herbNum; //허브 번호
  private String herbName; //허브 이름
  private float tempMin; //최저 온도
  private float tempMax; //최대 온도
  private float humidMix; //최저 습도
  private float humidMax; //최대 습도
  private float soilMin; //최소 토양 수분
  private float soilMax; //최대 토양 수분
  private int luxMin; //최소 조도
  private int luxMax; //최대 조도
  private String imgName; //작물 이미지 파일
}
