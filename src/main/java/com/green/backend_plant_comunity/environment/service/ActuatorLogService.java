package com.green.backend_plant_comunity.environment.service;

import com.green.backend_plant_comunity.environment.dto.ActuatorLogDTO;
import com.green.backend_plant_comunity.environment.mapper.ActuatorLogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActuatorLogService {
  private final ActuatorLogMapper actuatorLogMapper;

  public List<ActuatorLogDTO> getLogList(){
    return actuatorLogMapper.getLogList();
  }

}
