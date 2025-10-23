package com.green.backend_plant_comunity.chat.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ChatRoomDTO {
    private int roomId;
    private String roomName;
    private String roomType;        // DIRECT, GROUP
    private LocalDateTime createdAt;
    private LocalDateTime lastMessageAt;
    
    // 추가 정보
    private List<String> participantIds;  // 참여자 ID 목록
    private int participantCount;         // 참여자 수
    private String lastMessage;           // 마지막 메시지
    private int unreadCount;              // 안 읽은 메시지 수
}
