package com.green.backend_plant_comunity.environment.controller;

import com.green.backend_plant_comunity.environment.dto.EnvironmentDTO;
import com.green.backend_plant_comunity.environment.dto.StandardsDTO;
import com.green.backend_plant_comunity.environment.service.EnvironmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


  //특정 작물의 표준 값 조회
  @GetMapping("/standards/{herbNum}")
  public StandardsDTO getStandardsHerb(@PathVariable("herbNum") int herbNum){
    return environmentService.getStandardsHerb(herbNum);
  }

  // 사용자 (memId)가 키우는 작물 목록 조회
  // URL: /api/herbs/member/user123
  @GetMapping("/herbs/member/{memId}")
  public List<StandardsDTO> getUserHerbsByMemId(@PathVariable("memId") String memId){
    return environmentService.getUserHerbsByMemId(memId);
  }

  // 특정 작물 (herbNum)에 대한 센서 데이터 리스트 조회
  // URL: /api/sensor/{herbNum}
  @GetMapping("/sensor/{herbNum}")
  public List<EnvironmentDTO> getSensorDataByHerbNum(@PathVariable("herbNum") int herbNum){
    return environmentService.getSensorDataByHerbNum(herbNum);
  }
}
