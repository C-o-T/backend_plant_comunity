package com.green.backend_plant_comunity.environment.mapper;

import com.green.backend_plant_comunity.environment.dto.EnvironmentDTO;
import com.green.backend_plant_comunity.environment.dto.StandardsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EnvironmentMapper {

  //센서데이터 리스트 조회
  public List<EnvironmentDTO> getSensorData();

  //작물의 표준 값 조회
  public StandardsDTO getStandardsHerb(int herbNum);

  // memId가 키우는 작물 목록 조회
  public List<StandardsDTO> getUserHerbsByMemId(String memId);

  // 특정 작물 (herbNum)에 대한 센서 데이터 리스트 조회
  public List<EnvironmentDTO> getSensorDataByHerbNum(int herbNum);
}
