package com.green.backend_plant_comunity.message.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageDTO {
    private int msgNum;
    private String senderId;
    private String senderName;
    private String receiverId;
    private String receiverName;
    private String title;
    private String content;
    private boolean isRead;
    private boolean isDeletedBySender;
    private boolean isDeletedByReceiver;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;
}
