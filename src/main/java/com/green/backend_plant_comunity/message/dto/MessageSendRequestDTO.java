package com.green.backend_plant_comunity.message.dto;

import lombok.Data;

@Data
public class MessageSendRequestDTO {
    private String receiverId;
    private String title;
    private String content;
}
