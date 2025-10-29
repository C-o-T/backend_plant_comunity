package com.green.backend_plant_comunity.chat.controller;

import com.green.backend_plant_comunity.chat.dto.ChatMessageDTO;
import com.green.backend_plant_comunity.chat.dto.ChatParticipantDTO;
import com.green.backend_plant_comunity.chat.dto.ChatRoomDTO;
import com.green.backend_plant_comunity.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    
    private final ChatService chatService;
    
    // ==================== CHAT_ROOM ====================
    
    // 1:1 채팅방 생성
    @PostMapping("/room/direct")
    public ResponseEntity<Map<String, Integer>> createDirectChatRoom(
            @RequestParam String memId1,
            @RequestParam String memId2) {
        int roomId = chatService.createDirectChatRoom(memId1, memId2);
        Map<String, Integer> result = new HashMap<>();
        result.put("roomId", roomId);
        return ResponseEntity.ok(result);
    }
    
    // 단체 채팅방 생성
    @PostMapping("/room/group")
    public ResponseEntity<Map<String, Integer>> createGroupChatRoom(
            @RequestParam String roomName,
            @RequestBody List<String> memberIds) {
        int roomId = chatService.createGroupChatRoom(roomName, memberIds);
        Map<String, Integer> result = new HashMap<>();
        result.put("roomId", roomId);
        return ResponseEntity.ok(result);
    }
    
    // 채팅방 조회
    @GetMapping("/room/{roomId}")
    public ResponseEntity<ChatRoomDTO> getChatRoom(@PathVariable int roomId) {
        ChatRoomDTO chatRoom = chatService.getChatRoom(roomId);
        return ResponseEntity.ok(chatRoom);
    }
    
    // 내 채팅방 목록
    @GetMapping("/rooms/{memId}")
    public ResponseEntity<List<ChatRoomDTO>> getMyChatRooms(@PathVariable String memId) {
        List<ChatRoomDTO> chatRooms = chatService.getMyChatRooms(memId);
        return ResponseEntity.ok(chatRooms);
    }
    
    // ==================== CHAT_PARTICIPANT ====================
    
    // 참여자 추가
    @PostMapping("/room/{roomId}/participant/{memId}")
    public ResponseEntity<String> addParticipant(
            @PathVariable int roomId,
            @PathVariable String memId) {
        chatService.addParticipant(roomId, memId);
        return ResponseEntity.ok("참여자 추가 성공");
    }
    
    // 채팅방 참여자 목록
    @GetMapping("/room/{roomId}/participants")
    public ResponseEntity<List<ChatParticipantDTO>> getParticipants(@PathVariable int roomId) {
        List<ChatParticipantDTO> participants = chatService.getParticipants(roomId);
        return ResponseEntity.ok(participants);
    }
    
    // 채팅방 나가기
    @DeleteMapping("/room/{roomId}/leave/{memId}")
    public ResponseEntity<String> leaveChatRoom(
            @PathVariable int roomId,
            @PathVariable String memId) {
        chatService.leaveChatRoom(roomId, memId);
        return ResponseEntity.ok("채팅방 나가기 성공");
    }
    
    // 읽음 처리
    @PutMapping("/room/{roomId}/read/{memId}")
    public ResponseEntity<String> markAsRead(
            @PathVariable int roomId,
            @PathVariable String memId) {
        chatService.markAsRead(roomId, memId);
        return ResponseEntity.ok("읽음 처리 완료");
    }
    
    // ==================== CHAT_MESSAGE ====================
    
    // 메시지 전송
    @PostMapping("/message")
    public ResponseEntity<Map<String, Integer>> sendMessage(@RequestBody ChatMessageDTO chatMessageDTO) {
        int msgId = chatService.sendMessage(chatMessageDTO);
        Map<String, Integer> result = new HashMap<>();
        result.put("msgId", msgId);
        return ResponseEntity.ok(result);
    }
    
    // 채팅방 메시지 목록 (페이징)
    @GetMapping("/messages/{roomId}")
    public ResponseEntity<List<ChatMessageDTO>> getMessages(
            @PathVariable int roomId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "50") int size) {
        List<ChatMessageDTO> messages = chatService.getMessages(roomId, page, size);
        return ResponseEntity.ok(messages);
    }
    
    // 메시지 삭제
    @DeleteMapping("/message/{msgId}")
    public ResponseEntity<String> deleteMessage(@PathVariable int msgId) {
        chatService.deleteMessage(msgId);
        return ResponseEntity.ok("메시지 삭제 성공");
    }
    
    // 안 읽은 메시지 수
    @GetMapping("/unread/{memId}/{roomId}")
    public ResponseEntity<Map<String, Integer>> getUnreadCount(
            @PathVariable String memId,
            @PathVariable int roomId) {
        int unreadCount = chatService.getUnreadCount(memId, roomId);
        Map<String, Integer> result = new HashMap<>();
        result.put("unreadCount", unreadCount);
        return ResponseEntity.ok(result);
    }

    // 채팅 파일 업로드
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadChatFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("roomId") int roomId,
            @RequestParam("senderId") String senderId) throws IOException {
        Map<String, String> result = chatService.uploadChatFile(file, roomId, senderId);
        return ResponseEntity.ok(result);
    }
}
