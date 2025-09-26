package com.green.backend_plant_comunity.plant.service;

import com.green.backend_plant_comunity.plant.dto.PlantDTO;
import com.green.backend_plant_comunity.plant.mapper.PlantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantService {
  private final PlantMapper plantMapper;

  //식물 리스트 조회
  public List<PlantDTO> getPlantList(){
    return plantMapper.getPlantList();
  }

}
