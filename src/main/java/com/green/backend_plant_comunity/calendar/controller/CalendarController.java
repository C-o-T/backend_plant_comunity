package com.green.backend_plant_comunity.calendar.controller;

import com.green.backend_plant_comunity.calendar.dto.CalendarDTO;
import com.green.backend_plant_comunity.calendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("diaries")
public class CalendarController {
    
    private final CalendarService calendarService;
    
    // 1. GET /api/diaries - 일기 목록 조회
    @GetMapping("")
    public ResponseEntity<?> getDiaries(@RequestParam("memId") String memId) {
        try {
            List<CalendarDTO> diaries = calendarService.getDiaries(memId);
            return ResponseEntity.status(HttpStatus.OK).body(diaries);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("일기 목록 조회 중 오류가 발생했습니다.");
        }
    }
    
    // 2. POST /api/diaries - 새 일기 작성
    @PostMapping("")
    public ResponseEntity<?> insertDiary(@RequestBody CalendarDTO calendarDTO) {
        try {
            calendarService.insertDiary(calendarDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("일기가 작성되었습니다.");
        } catch (RuntimeException e) {
            // 같은 날짜에 이미 일기가 존재하는 경우
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("일기 작성 중 오류가 발생했습니다.");
        }
    }
    
    // 3. PUT /api/diaries/:diaryId - 일기 수정
    @PutMapping("/{diaryId}")
    public ResponseEntity<?> updateDiary(
            @PathVariable("diaryId") int diaryId,
            @RequestBody CalendarDTO calendarDTO) {
        try {
            calendarDTO.setDiaryId(diaryId);
            calendarService.updateDiary(calendarDTO);
            return ResponseEntity.status(HttpStatus.OK).body("일기가 수정되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("일기 수정 중 오류가 발생했습니다.");
        }
    }
    
    // 4. DELETE /api/diaries/:diaryId - 일기 삭제
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<?> deleteDiary(@PathVariable("diaryId") int diaryId) {
        try {
            calendarService.deleteDiary(diaryId);
            return ResponseEntity.status(HttpStatus.OK).body("일기가 삭제되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("일기 삭제 중 오류가 발생했습니다.");
        }
    }
}
