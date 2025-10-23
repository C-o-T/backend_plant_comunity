package com.green.backend_plant_comunity.chat.service;

import com.green.backend_plant_comunity.chat.dto.ChatMessageDTO;
import com.green.backend_plant_comunity.chat.dto.ChatParticipantDTO;
import com.green.backend_plant_comunity.chat.dto.ChatRoomDTO;
import com.green.backend_plant_comunity.chat.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    
    private final ChatMapper chatMapper;
    
    // ==================== CHAT_ROOM ====================
    
    // 채팅방 생성 (1:1)
    @Transactional
    public int createDirectChatRoom(String memId1, String memId2) {
        ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
        chatRoomDTO.setRoomType("DIRECT");
        
        chatMapper.insertChatRoom(chatRoomDTO);
        int roomId = chatRoomDTO.getRoomId();
        
        chatMapper.insertParticipant(roomId, memId1);
        chatMapper.insertParticipant(roomId, memId2);
        
        return roomId;
    }
    
    // 채팅방 생성 (단체)
    @Transactional
    public int createGroupChatRoom(String roomName, List<String> memberIds) {
        ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
        chatRoomDTO.setRoomName(roomName);
        chatRoomDTO.setRoomType("GROUP");
        
        chatMapper.insertChatRoom(chatRoomDTO);
        int roomId = chatRoomDTO.getRoomId();
        
        for (String memId : memberIds) {
            chatMapper.insertParticipant(roomId, memId);
        }
        
        return roomId;
    }
    
    // 채팅방 조회
    public ChatRoomDTO getChatRoom(int roomId) {
        return chatMapper.getChatRoom(roomId);
    }
    
    // 내 채팅방 목록
    public List<ChatRoomDTO> getMyChatRooms(String memId) {
        return chatMapper.getMyChatRooms(memId);
    }
    
    // ==================== CHAT_PARTICIPANT ====================
    
    // 참여자 추가
    @Transactional
    public void addParticipant(int roomId, String memId) {
        chatMapper.insertParticipant(roomId, memId);
    }
    
    // 채팅방 참여자 목록
    public List<ChatParticipantDTO> getParticipants(int roomId) {
        return chatMapper.getParticipants(roomId);
    }
    
    // 채팅방 나가기
    @Transactional
    public void leaveChatRoom(int roomId, String memId) {
        chatMapper.leaveChatRoom(roomId, memId);
    }
    
    // 마지막 읽은 시간 업데이트
    @Transactional
    public void markAsRead(int roomId, String memId) {
        chatMapper.updateLastReadAt(roomId, memId);
    }
    
    // ==================== CHAT_MESSAGE ====================
    
    // 메시지 전송
    @Transactional
    public int sendMessage(ChatMessageDTO chatMessageDTO) {
        chatMapper.insertMessage(chatMessageDTO);
        chatMapper.updateLastMessageAt(chatMessageDTO.getRoomId());
        return chatMessageDTO.getMsgId();
    }
    
    // 채팅방 메시지 목록
    public List<ChatMessageDTO> getMessages(int roomId, int page, int size) {
        int offset = (page - 1) * size;
        return chatMapper.getMessages(roomId, size, offset);
    }
    
    // 메시지 삭제
    @Transactional
    public void deleteMessage(int msgId) {
        chatMapper.deleteMessage(msgId);
    }
    
    // 안 읽은 메시지 수
    public int getUnreadCount(String memId, int roomId) {
        return chatMapper.getUnreadCount(memId, roomId);
    }
}
