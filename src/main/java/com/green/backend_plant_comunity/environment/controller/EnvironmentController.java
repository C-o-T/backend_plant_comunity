package com.green.backend_plant_comunity.environment.controller;

import com.green.backend_plant_comunity.environment.dto.EnvironmentDTO;
import com.green.backend_plant_comunity.environment.service.EnvironmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EnvironmentController {
  private final EnvironmentService environmentService;

  //센서데이터 리스트 조회
  @GetMapping("/sensor")
  public List<EnvironmentDTO> getSensorData(){
    return environmentService.getSensorData();
  }

}
