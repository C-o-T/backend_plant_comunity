package com.green.backend_plant_comunity.calendar.mapper;

import com.green.backend_plant_comunity.calendar.dto.CalendarDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CalendarMapper {
    
    // 일기 목록 조회 (사용자별)
    List<CalendarDTO> getDiaries(@Param("memId") String memId);
    
    // 일기 작성
    int insertDiary(CalendarDTO calendarDTO);
    
    // 일기 수정
    int updateDiary(CalendarDTO calendarDTO);
    
    // 일기 삭제
    int deleteDiary(@Param("diaryId") int diaryId);
    
    // 특정 날짜에 일기가 존재하는지 확인
    int checkDiaryExists(@Param("memId") String memId, @Param("diaryDate") String diaryDate);
}
