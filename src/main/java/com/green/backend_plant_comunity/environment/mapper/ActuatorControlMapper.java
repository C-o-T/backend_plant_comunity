package com.green.backend_plant_comunity.environment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.green.backend_plant_comunity.environment.dto.ActuatorLogDTO;

import com.green.backend_plant_comunity.environment.dto.ActuatorControlDTO;

@Mapper
public interface ActuatorControlMapper {

  // 제어 명령 저장
  void insertControlCommand(ActuatorControlDTO dto);

  // 특정 액추에이터의 최신 상태 조회
  ActuatorLogDTO getLatestStatus(@Param("raspNum") String raspNum, @Param("actName") String actName);

  // 액추에이터 로그 조회 (필터링 가능)
  List<ActuatorLogDTO> getActuatorLogs(@Param("raspNum") String raspNum, 
                                        @Param("actName") String actName, 
                                        @Param("limit") int limit);

  // 제어 명령 히스토리 조회
  List<ActuatorControlDTO> getControlHistory(@Param("raspNum") String raspNum,
                                              @Param("actName") String actName);

  // 오래된 제어 명령 정리 (1시간 이상 지난 PROCESSED=TRUE 레코드 삭제)
  void cleanupOldCommands();
}