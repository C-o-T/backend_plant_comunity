package com.green.backend_plant_comunity.calendar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.green.backend_plant_comunity.calendar.dto.CalendarEventDTO;

import lombok.RequiredArgsConstructor;
import com.green.backend_plant_comunity.calendar.mapper.WateringMapper;

@Service
@RequiredArgsConstructor
public class WateringService {

    private final WateringMapper wateringMapper;

    // 물주기 일정 추가
    public void insertWatering(CalendarEventDTO dto) {
        wateringMapper.insertWatering(dto);
    }

    // 특정 회원의 물주기 일정 조회
    public List<CalendarEventDTO> getWateringsByMemId(String memId) {
        return wateringMapper.getWateringsByMemId(memId);
    }

    // 물주기 일정 삭제
    public void deleteWatering(Long wateringId) {
        wateringMapper.deleteWatering(wateringId);
    }
}
