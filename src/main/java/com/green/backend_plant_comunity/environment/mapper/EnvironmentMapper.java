package com.green.backend_plant_comunity.environment.mapper;

import com.green.backend_plant_comunity.environment.dto.EnvironmentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EnvironmentMapper {

  //센서데이터 리스트 조회
  public List<EnvironmentDTO> getSensorData();

  //마지막 센서데이터만 조회
  public EnvironmentDTO getLastSensorData();

}
