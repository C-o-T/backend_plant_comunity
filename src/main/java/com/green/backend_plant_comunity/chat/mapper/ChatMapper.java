package com.green.backend_plant_comunity.chat.mapper;

import com.green.backend_plant_comunity.chat.dto.ChatMessageDTO;
import com.green.backend_plant_comunity.chat.dto.ChatParticipantDTO;
import com.green.backend_plant_comunity.chat.dto.ChatRoomDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatMapper {
    
    // ==================== CHAT_ROOM ====================
    
    // 채팅방 생성
    void insertChatRoom(ChatRoomDTO chatRoomDTO);
    
    // 채팅방 조회
    ChatRoomDTO getChatRoom(@Param("roomId") int roomId);
    
    // 내 채팅방 목록
    List<ChatRoomDTO> getMyChatRooms(@Param("memId") String memId);
    
    // 마지막 메시지 시간 업데이트
    void updateLastMessageAt(@Param("roomId") int roomId);

    // 1:1 채팅방 존재 여부 확인
    Integer findDirectChatRoom(@Param("memId1") String memId1, @Param("memId2") String memId2);

    // 채팅방 타입 변경
    void updateRoomType(@Param("roomId") int roomId, @Param("roomType") String roomType);

    // 채팅방 이름 변경
    void updateRoomName(@Param("roomId") int roomId, @Param("roomName") String roomName);

    // ==================== CHAT_PARTICIPANT ====================
    
    // 참여자 추가
    void insertParticipant(@Param("roomId") int roomId, @Param("memId") String memId);

    // 참여자 재활성화 (나갔다가 다시 들어올 때)
    void reactivateParticipant(@Param("roomId") int roomId, @Param("memId") String memId);

    // 참여자 존재 여부 확인
    Integer checkParticipantExists(@Param("roomId") int roomId, @Param("memId") String memId);

    // 채팅방 참여자 목록
    List<ChatParticipantDTO> getParticipants(@Param("roomId") int roomId);
    
    // 채팅방 나가기
    void leaveChatRoom(@Param("roomId") int roomId, @Param("memId") String memId);
    
    // 마지막 읽은 시간 업데이트
    void updateLastReadAt(@Param("roomId") int roomId, @Param("memId") String memId);
    
    // ==================== CHAT_MESSAGE ====================
    
    // 메시지 전송
    void insertMessage(ChatMessageDTO chatMessageDTO);
    
    // 채팅방 메시지 목록
    List<ChatMessageDTO> getMessages(@Param("roomId") int roomId, 
                                     @Param("limit") int limit, 
                                     @Param("offset") int offset);
    
    // 메시지 삭제
    void deleteMessage(@Param("msgId") int msgId);
    
    // 안 읽은 메시지 수
    int getUnreadCount(@Param("memId") String memId, @Param("roomId") int roomId);

    // ==================== CHAT_ROOM DELETE ====================

    // 활성 참여자 수 조회
    int countActiveParticipants(@Param("roomId") int roomId);

    // 채팅방 메시지 삭제
    void deleteChatMessages(@Param("roomId") int roomId);

    // 채팅방 참여자 삭제
    void deleteChatParticipants(@Param("roomId") int roomId);

    // 채팅방 삭제
    void deleteChatRoom(@Param("roomId") int roomId);
}
