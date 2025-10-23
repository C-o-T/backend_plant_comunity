package com.green.backend_plant_comunity.chat.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageDTO {
    private int msgId;
    private int roomId;
    private String senderId;
    private String messageType;     // TEXT, IMAGE, FILE
    private String content;
    private String fileUrl;
    private LocalDateTime sentAt;
    private boolean isDeleted;
    
    // 추가 정보
    private String senderName;
    private String senderEmail;
}
