package com.green.backend_plant_comunity.environment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.green.backend_plant_comunity.environment.dto.ActuatorLogDTO;

import lombok.RequiredArgsConstructor;
import com.green.backend_plant_comunity.environment.dto.ActuatorControlDTO;
import com.green.backend_plant_comunity.environment.service.ActuatorControlService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/control")
public class ActuatorController {
  private final ActuatorControlService actuatorControlService;

  // 액추에이터 수동 제어
  @PostMapping("/control")
  public ResponseEntity<?> controlActuator(@RequestBody ActuatorControlDTO dto) {
    try {
      actuatorControlService.controlActuator(dto);
      
      Map<String, String> response = new HashMap<>();
      response.put("message", "제어 명령이 전송되었습니다");
      response.put("actName", dto.getActName());
      response.put("command", dto.getCommand());
      
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("제어 명령 전송 실패");
    }
  }

  // 모드 변경 (AUTO/MANUAL)
  @PostMapping("/mode")
  public ResponseEntity<?> changeMode(@RequestBody ActuatorControlDTO dto) {
    try {
      // AUTO 모드로 변경하는 명령 저장
      dto.setCommand("AUTO");
      actuatorControlService.controlActuator(dto);
      
      Map<String, String> response = new HashMap<>();
      response.put("message", "모드가 변경되었습니다");
      response.put("actName", dto.getActName());
      response.put("mode", dto.getMode());
      
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("모드 변경 실패");
    }
  }

  // 액추에이터 상태 조회
  @GetMapping("/status")
  public ResponseEntity<?> getActuatorStatus(@RequestParam String raspNum, 
                                              @RequestParam String actName) {
    try {
      ActuatorLogDTO status = actuatorControlService.getActuatorStatus(raspNum, actName);
      
      if (status == null) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "상태 정보가 없습니다");
        response.put("actName", actName);
        response.put("state", "UNKNOWN");
        return ResponseEntity.status(HttpStatus.OK).body(response);
      }
      
      return ResponseEntity.status(HttpStatus.OK).body(status);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상태 조회 실패");
    }
  }

  // 액추에이터 로그 조회
  @GetMapping("/logs")
  public ResponseEntity<?> getActuatorLogs(@RequestParam String raspNum,
                                            @RequestParam(required = false) String actName,
                                            @RequestParam(defaultValue = "50") int limit) {
    try {
      List<ActuatorLogDTO> logs = actuatorControlService.getActuatorLogs(raspNum, actName, limit);
      return ResponseEntity.status(HttpStatus.OK).body(logs);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그 조회 실패");
    }
  }

  // 제어 명령 히스토리 조회
  @GetMapping("/history")
  public ResponseEntity<?> getControlHistory(@RequestParam String raspNum,
                                              @RequestParam(required = false) String actName) {
    try {
      List<ActuatorControlDTO> history = actuatorControlService.getControlHistory(raspNum, actName);
      return ResponseEntity.status(HttpStatus.OK).body(history);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("히스토리 조회 실패");
    }
  }

  // 오래된 제어 명령 정리 (1시간 이상 지난 PROCESSED=TRUE 레코드 삭제)
  @DeleteMapping("/cleanup")
  public ResponseEntity<?> cleanupOldCommands() {
    try {
      actuatorControlService.cleanupOldCommands();
      Map<String, String> response = new HashMap<>();
      response.put("message", "오래된 제어 명령이 정리되었습니다");
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("정리 작업 실패");
    }
  }
}
