package com.green.backend_plant_comunity.environment.mapper;

import com.green.backend_plant_comunity.environment.dto.ActuatorLogDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActuatorLogMapper {

  public List<ActuatorLogDTO> getLogList();

}
