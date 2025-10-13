package com.green.backend_plant_comunity.calendar.service;

import com.green.backend_plant_comunity.calendar.dto.CalendarDTO;
import com.green.backend_plant_comunity.calendar.mapper.CalendarMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {
    
    private final CalendarMapper calendarMapper;
    
    // 일기 목록 조회
    public List<CalendarDTO> getDiaries(String memId) {
        return calendarMapper.getDiaries(memId);
    }
    
    // 일기 작성
    public void insertDiary(CalendarDTO calendarDTO) {
        // 같은 날짜에 일기가 있는지 확인
        int exists = calendarMapper.checkDiaryExists(
            calendarDTO.getMemId(), 
            calendarDTO.getDiaryDate().toString()
        );
        
        if (exists > 0) {
            throw new RuntimeException("해당 날짜에 이미 일기가 존재합니다.");
        }
        
        calendarMapper.insertDiary(calendarDTO);
    }
    
    // 일기 수정
    public void updateDiary(CalendarDTO calendarDTO) {
        calendarMapper.updateDiary(calendarDTO);
    }
    
    // 일기 삭제
    public void deleteDiary(int diaryId) {
        calendarMapper.deleteDiary(diaryId);
    }
}
