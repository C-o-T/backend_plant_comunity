package com.green.backend_plant_comunity.plant.mapper;

import com.green.backend_plant_comunity.plant.dto.PlantDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlantMapper {
  //식물 리스트 조회
  public List<PlantDTO> getPlantList();

}
