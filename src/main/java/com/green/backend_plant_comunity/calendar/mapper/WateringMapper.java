package com.green.backend_plant_comunity.calendar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.green.backend_plant_comunity.calendar.dto.CalendarEventDTO;

@Mapper
public interface WateringMapper {
    // 물주기 일정 추가
    int insertWatering(CalendarEventDTO dto);
    
    // 특정 회원의 물주기 일정 조회
    List<CalendarEventDTO> getWateringsByMemId(@Param("memId") String memId);
    
    // 물주기 일정 삭제
    int deleteWatering(@Param("wateringId") Long wateringId);
}
