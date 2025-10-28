package com.green.backend_plant_comunity.environment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.green.backend_plant_comunity.environment.dto.ActuatorLogDTO;

import lombok.RequiredArgsConstructor;
import com.green.backend_plant_comunity.environment.dto.ActuatorControlDTO;
import com.green.backend_plant_comunity.environment.mapper.ActuatorControlMapper;

@Service
@RequiredArgsConstructor
public class ActuatorControlService {
  private final ActuatorControlMapper actuatorControlMapper;

  // 액추에이터 제어 명령 저장
  public void controlActuator(ActuatorControlDTO dto) {
    actuatorControlMapper.insertControlCommand(dto);
  }

  // 특정 액추에이터의 최신 상태 조회
  public ActuatorLogDTO getActuatorStatus(String raspNum, String actName) {
    return actuatorControlMapper.getLatestStatus(raspNum, actName);
  }

  // 액추에이터 로그 조회
  public List<ActuatorLogDTO> getActuatorLogs(String raspNum, String actName, int limit) {
    return actuatorControlMapper.getActuatorLogs(raspNum, actName, limit);
  }

  // 제어 명령 히스토리 조회
  public List<ActuatorControlDTO> getControlHistory(String raspNum, String actName) {
    return actuatorControlMapper.getControlHistory(raspNum, actName);
  }

  // 오래된 제어 명령 정리
  public void cleanupOldCommands() {
    actuatorControlMapper.cleanupOldCommands();
  }
}
