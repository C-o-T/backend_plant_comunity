package com.green.backend_plant_comunity.chat.service;

import com.green.backend_plant_comunity.chat.dto.ChatMessageDTO;
import com.green.backend_plant_comunity.chat.dto.ChatParticipantDTO;
import com.green.backend_plant_comunity.chat.dto.ChatRoomDTO;
import com.green.backend_plant_comunity.chat.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMapper chatMapper;

    @Value("${file.upload-dir:D:/01-STUDY/dev/team/upload/}")
    private String uploadDir;
    
    // ==================== CHAT_ROOM ====================
    
    // 채팅방 생성 (1:1)
    @Transactional
    public int createDirectChatRoom(String memId1, String memId2) {
        // 기존 채팅방이 있는지 확인
        Integer existingRoomId = chatMapper.findDirectChatRoom(memId1, memId2);

        if (existingRoomId != null) {
            // 이미 존재하면 기존 채팅방 ID 반환
            return existingRoomId;
        }

        // 새로운 채팅방 생성
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
        // 이미 참여한 적이 있는지 확인
        Integer existsCount = chatMapper.checkParticipantExists(roomId, memId);

        if (existsCount != null && existsCount > 0) {
            // 이미 참여한 적이 있으면 재활성화
            chatMapper.reactivateParticipant(roomId, memId);
            System.out.println("✅ 참여자 재활성화: 채팅방 " + roomId + ", 사용자 " + memId);
        } else {
            // 처음 참여하는 경우 새로 추가
            chatMapper.insertParticipant(roomId, memId);
            System.out.println("✅ 새 참여자 추가: 채팅방 " + roomId + ", 사용자 " + memId);
        }

        // 채팅방 정보 조회
        ChatRoomDTO chatRoom = chatMapper.getChatRoom(roomId);

        // DIRECT 타입이면서 활성 참여자가 3명 이상이면 GROUP으로 변경
        if ("DIRECT".equals(chatRoom.getRoomType())) {
            int activeParticipants = chatMapper.countActiveParticipants(roomId);
            if (activeParticipants >= 3) {
                // 채팅방 타입을 GROUP으로 변경 (이름은 프론트엔드에서 동적으로 표시)
                chatMapper.updateRoomType(roomId, "GROUP");
                System.out.println("🔄 채팅방 " + roomId + " 타입 변경: DIRECT → GROUP (참여자 " + activeParticipants + "명)");
            }
        }
    }
    
    // 채팅방 참여자 목록
    public List<ChatParticipantDTO> getParticipants(int roomId) {
        return chatMapper.getParticipants(roomId);
    }
    
    // 채팅방 나가기
    @Transactional
    public void leaveChatRoom(int roomId, String memId) {
        // 채팅방 나가기 (참여자 IS_ACTIVE = FALSE)
        chatMapper.leaveChatRoom(roomId, memId);

        // 남은 활성 참여자 수 확인
        int activeParticipants = chatMapper.countActiveParticipants(roomId);

        // 참여자가 0명이면 채팅방 완전 삭제
        if (activeParticipants == 0) {
            // 1. 메시지 삭제
            chatMapper.deleteChatMessages(roomId);
            // 2. 참여자 삭제
            chatMapper.deleteChatParticipants(roomId);
            // 3. 채팅방 삭제
            chatMapper.deleteChatRoom(roomId);

            System.out.println("📌 채팅방 " + roomId + " 완전 삭제 (참여자 0명)");
        }
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

    // ==================== FILE UPLOAD ====================

    // 채팅 파일 업로드
    @Transactional
    public Map<String, String> uploadChatFile(MultipartFile file, int roomId, String senderId) throws IOException {
        // 채팅 전용 디렉토리 생성
        String chatDir = uploadDir + "chat/";
        File dir = new File(chatDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 파일명 생성 (중복 방지)
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String newFilename = "room" + roomId + "_" + senderId + "_" + System.currentTimeMillis() + extension;

        // 파일 저장
        File dest = new File(chatDir + newFilename);
        System.out.println("파일 저장 경로: " + dest.getAbsolutePath());
        file.transferTo(dest);
        System.out.println("파일 저장 완료: " + dest.exists());

        // 파일 URL 생성
        String fileUrl = "/upload/chat/" + newFilename;

        // 결과 반환
        Map<String, String> result = new HashMap<>();
        result.put("fileUrl", fileUrl);
        result.put("fileName", originalFilename);
        result.put("message", "파일 업로드 성공");

        return result;
    }
}
