package com.green.backend_plant_comunity.message.controller;

import com.green.backend_plant_comunity.message.dto.MessageDTO;
import com.green.backend_plant_comunity.message.dto.MessageSendRequestDTO;
import com.green.backend_plant_comunity.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    
    // 쪽지 보내기
    @PostMapping("/{senderId}")
    public ResponseEntity<String> sendMessage(@PathVariable String senderId, @RequestBody MessageSendRequestDTO request) {
        try {
            messageService.sendMessage(senderId, request);
            return ResponseEntity.ok("쪽지 전송 성공");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("쪽지 전송 실패");
        }
    }
    
    // 받은 쪽지함
    @GetMapping("/box/received/{memberId}")
    public ResponseEntity<List<MessageDTO>> getReceivedMessages(@PathVariable String memberId) {
        try {
            List<MessageDTO> messages = messageService.getReceivedMessages(memberId);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // 보낸 쪽지함
    @GetMapping("/box/sent/{memberId}")
    public ResponseEntity<List<MessageDTO>> getSentMessages(@PathVariable String memberId) {
        try {
            List<MessageDTO> messages = messageService.getSentMessages(memberId);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 쪽지 읽기
    @GetMapping("/{messageId}")
    public ResponseEntity<MessageDTO> readMessage(@PathVariable int messageId) {
        try {
            System.out.println("=== 쪽지 조회 시작 ===");
            System.out.println("messageId: " + messageId);

            MessageDTO message = messageService.readMessage(messageId);

            System.out.println("조회 성공: " + message);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            System.err.println("=== 쪽지 조회 에러 ===");
            e.printStackTrace(); // 전체 에러 스택 출력
            return ResponseEntity.badRequest().build();
        }
    }
    
    // 안 읽은 쪽지 개수
    @GetMapping("/unread/count/{memberId}")
    public ResponseEntity<Integer> getUnreadCount(@PathVariable String memberId) {
        try {
            int count = messageService.getUnreadCount(memberId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.ok(0);
        }
    }
    
    // 쪽지 삭제
    @DeleteMapping("/{messageId}/{memberId}")
    public ResponseEntity<String> deleteMessage(@PathVariable int messageId, @PathVariable String memberId) {
        try {
            messageService.deleteMessage(messageId, memberId);
            return ResponseEntity.ok("쪽지 삭제 성공");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("쪽지 삭제 실패");
        }
    }
}
