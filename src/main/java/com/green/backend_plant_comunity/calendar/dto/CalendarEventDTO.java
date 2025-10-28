package com.green.backend_plant_comunity.calendar.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CalendarEventDTO {
    private Long wateringId;
    private String memId;
    private String plantName;
    private LocalDate wateringDate;
    private Integer cycleDay;
    private LocalDateTime createdAt;
}
