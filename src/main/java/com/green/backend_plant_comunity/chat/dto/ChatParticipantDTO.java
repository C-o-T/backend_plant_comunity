package com.green.backend_plant_comunity.chat.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatParticipantDTO {
    private int participantId;
    private int roomId;
    private String memId;
    private LocalDateTime joinedAt;
    private LocalDateTime lastReadAt;
    private boolean isActive;
    
    // 추가 정보
    private String memName;
    private String memEmail;
}
