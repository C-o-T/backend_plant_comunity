package com.green.backend_plant_comunity.calendar.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CalendarDTO {
    private int diaryId;           // diary_id
    private String memId;          // mem_id
    private String diaryTitle;     // diary_title
    private String diaryContent;   // diary_content
    private LocalDate diaryDate;   // diary_date
    private LocalDateTime createdAt;    // created_at
    private LocalDateTime updatedAt;    // updated_at
}
