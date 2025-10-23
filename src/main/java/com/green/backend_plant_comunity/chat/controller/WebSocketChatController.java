package com.green.backend_plant_comunity.chat.controller;

import com.green.backend_plant_comunity.chat.dto.ChatMessageDTO;
import com.green.backend_plant_comunity.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class WebSocketChatController {
    
    private final ChatService chatService;
    
    // 채팅방에 메시지 전송
    @MessageMapping("/chat.send/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessageDTO sendMessage(@Payload ChatMessageDTO chatMessageDTO) {
        // 디버깅 로그 추가
        System.out.println("📨 메시지 수신: " + chatMessageDTO.getContent());
        System.out.println("📤 브로드캐스트 대상: /topic/room/" + chatMessageDTO.getRoomId());

        // DB에 메시지 저장 (임시 주석 - 테스트용)
        chatMessageDTO.setSentAt(LocalDateTime.now());
        // chatService.sendMessage(chatMessageDTO);  // TODO: 로그인 기능 구현 후 활성화

        // 실시간으로 모든 구독자에게 전송
        return chatMessageDTO;
    }
    
    // 사용자가 채팅방에 입장
    @MessageMapping("/chat.join/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessageDTO joinRoom(@Payload ChatMessageDTO chatMessageDTO, 
                                   SimpMessageHeaderAccessor headerAccessor) {
        // 세션에 사용자 정보 저장
        headerAccessor.getSessionAttributes().put("username", chatMessageDTO.getSenderId());
        
        chatMessageDTO.setContent(chatMessageDTO.getSenderId() + "님이 입장하셨습니다.");
        chatMessageDTO.setMessageType("SYSTEM");
        chatMessageDTO.setSentAt(LocalDateTime.now());
        
        return chatMessageDTO;
    }
    
    // 사용자가 채팅방에서 퇴장
    @MessageMapping("/chat.leave/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessageDTO leaveRoom(@Payload ChatMessageDTO chatMessageDTO) {
        chatMessageDTO.setContent(chatMessageDTO.getSenderId() + "님이 퇴장하셨습니다.");
        chatMessageDTO.setMessageType("SYSTEM");
        chatMessageDTO.setSentAt(LocalDateTime.now());
        
        return chatMessageDTO;
    }
    
    // 입력 중 표시
    @MessageMapping("/chat.typing/{roomId}")
    @SendTo("/topic/room/{roomId}/typing")
    public String typing(@Payload String senderId) {
        return senderId + "님이 입력 중...";
    }
}
