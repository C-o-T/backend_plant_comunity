package com.green.backend_plant_comunity.plant.controller;

import com.green.backend_plant_comunity.plant.dto.PlantDTO;
import com.green.backend_plant_comunity.plant.service.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/plants")
public class PlantController {
  private final PlantService plantService;

  //식물 리스트 조회
  @GetMapping("")
  public List<PlantDTO> getPlantList(){
    return plantService.getPlantList();
  }


}
