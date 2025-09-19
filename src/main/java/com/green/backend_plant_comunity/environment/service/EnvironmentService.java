package com.green.backend_plant_comunity.environment.service;

import com.green.backend_plant_comunity.environment.dto.EnvironmentDTO;
import com.green.backend_plant_comunity.environment.dto.StandardsDTO;
import com.green.backend_plant_comunity.environment.mapper.EnvironmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnvironmentService {
  private final EnvironmentMapper environmentMapper;

  //센서데이터 리스트 조회
  public List<EnvironmentDTO> getSensorData(){
    return environmentMapper.getSensorData();
  }

  //특정 작물의 표준 값 조회
  public StandardsDTO getStandardsHerb(int herbNum){
    return environmentMapper.getStandardsHerb(herbNum);
  }

  // 사용자 (memId)가 키우는 작물 목록 조회
  public List<StandardsDTO> getUserHerbsByMemId(String memId){
    return environmentMapper.getUserHerbsByMemId(memId);
  }

  // 특정 작물 (herbNum)에 대한 센서 데이터 리스트 조회
  public List<EnvironmentDTO> getSensorDataByHerbNum(int herbNum){
    return environmentMapper.getSensorDataByHerbNum(herbNum);
  }

}
