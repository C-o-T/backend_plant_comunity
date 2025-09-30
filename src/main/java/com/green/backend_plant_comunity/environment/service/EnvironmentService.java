package com.green.backend_plant_comunity.environment.service;

import com.green.backend_plant_comunity.environment.dto.EnvironmentDTO;
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

  //마지막 센서데이터만 조회
  public EnvironmentDTO getLastSensorData(){
    return environmentMapper.getLastSensorData();
  }

}
