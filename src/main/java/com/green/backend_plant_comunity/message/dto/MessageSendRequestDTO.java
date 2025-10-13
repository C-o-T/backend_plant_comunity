package com.green.backend_plant_comunity.message.dto;

import lombok.Data;
import java.util.List;

@Data
public class MessageSendRequestDTO {
    private String receiverId;  // 단일 발송용 (하위 호환성)
    private List<String> receiverIds;  // 다중 발송용
    private boolean sendToAll;  // 전체 발송 여부
    private String title;
    private String content;
}
