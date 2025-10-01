package com.green.backend_plant_comunity.message.controller;

import com.green.backend_plant_comunity.message.dto.MessageDTO;
import com.green.backend_plant_comunity.message.dto.MessageSendRequestDTO;
import com.green.backend_plant_comunity.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    
    // 쪽지 보내기
    @PostMapping("")
    public void sendMessage(@RequestBody MessageSendRequestDTO request, HttpSession session) {
        String memberId = (String) session.getAttribute("MEM_ID");
        messageService.sendMessage(memberId, request);
    }
    
    // 받은 쪽지함
    @GetMapping("/received")
    public List<MessageDTO> getReceivedMessages(HttpSession session) {
        String memberId = (String) session.getAttribute("MEM_ID");
        return messageService.getReceivedMessages(memberId);
    }
    
    // 보낸 쪽지함
    @GetMapping("/sent")
    public List<MessageDTO> getSentMessages(HttpSession session) {
        String memberId = (String) session.getAttribute("MEM_ID");
        return messageService.getSentMessages(memberId);
    }
    
    // 쪽지 읽기
    @GetMapping("/{messageId}")
    public MessageDTO readMessage(@PathVariable int messageId, HttpSession session) {
        String memberId = (String) session.getAttribute("MEM_ID");
        return messageService.readMessage(messageId, memberId);
    }
    
    // 안 읽은 쪽지 개수
    @GetMapping("/unread/count")
    public int getUnreadCount(HttpSession session) {
        String memberId = (String) session.getAttribute("MEM_ID");
        return messageService.getUnreadCount(memberId);
    }
    
    // 쪽지 삭제
    @DeleteMapping("/{messageId}")
    public void deleteMessage(@PathVariable int messageId, HttpSession session) {
        String memberId = (String) session.getAttribute("MEM_ID");
        messageService.deleteMessage(messageId, memberId);
    }
}
