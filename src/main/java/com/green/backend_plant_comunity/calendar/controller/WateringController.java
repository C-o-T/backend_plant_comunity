package com.green.backend_plant_comunity.calendar.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.green.backend_plant_comunity.calendar.dto.CalendarEventDTO;

import lombok.RequiredArgsConstructor;
import com.green.backend_plant_comunity.calendar.service.WateringService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/watering")
public class WateringController {

    private final WateringService wateringService;

    // 물주기 일정 추가
    @PostMapping
    public ResponseEntity<?> addWatering(@RequestBody CalendarEventDTO dto) {
        try {
            wateringService.insertWatering(dto);
            Map<String, String> response = new HashMap<>();
            response.put("message", "물주기 일정이 추가되었습니다");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("물주기 일정 추가 실패");
        }
    }

    // 특정 회원의 물주기 일정 조회
    @GetMapping
    public ResponseEntity<?> getWaterings(@RequestParam String memId) {
        try {
            List<CalendarEventDTO> waterings = wateringService.getWateringsByMemId(memId);
            return ResponseEntity.status(HttpStatus.OK).body(waterings);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("물주기 일정 조회 실패");
        }
    }

    // 물주기 일정 삭제
    @DeleteMapping("/{wateringId}")
    public ResponseEntity<?> deleteWatering(@PathVariable Long wateringId) {
        try {
            wateringService.deleteWatering(wateringId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "물주기 일정이 삭제되었습니다");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("물주기 일정 삭제 실패");
        }
    }
}
