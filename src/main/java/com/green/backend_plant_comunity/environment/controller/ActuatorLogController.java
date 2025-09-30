package com.green.backend_plant_comunity.environment.controller;

import com.green.backend_plant_comunity.environment.dto.ActuatorLogDTO;
import com.green.backend_plant_comunity.environment.service.ActuatorLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/logs")
public class ActuatorLogController {
  private final ActuatorLogService actuatorLogService;

  @GetMapping("")
  public ResponseEntity<?> getLogList(){
    try {
      List<ActuatorLogDTO> logList =actuatorLogService.getLogList();
      return ResponseEntity.status(HttpStatus.OK).body(logList);
    }catch (Exception e){
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그조회 실패");
    }
  }

}
