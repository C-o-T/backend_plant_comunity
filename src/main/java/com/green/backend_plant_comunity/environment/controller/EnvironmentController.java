package com.green.backend_plant_comunity.environment.controller;

import com.green.backend_plant_comunity.environment.dto.EnvironmentDTO;
import com.green.backend_plant_comunity.environment.service.EnvironmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sensor")
public class EnvironmentController {
  private final EnvironmentService environmentService;

  //센서데이터 리스트 조회
  @GetMapping("")
  public List<EnvironmentDTO> getSensorData(){
    return environmentService.getSensorData();
  }


  @GetMapping("/last")
  //마지막 센서데이터만 조회
  public EnvironmentDTO getLastSensorData(){
    return environmentService.getLastSensorData();
  }
}
