package com.green.backend_plant_comunity.message.mapper;

import com.green.backend_plant_comunity.message.dto.MessageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    
    // 쪽지 보내기
    void insertMessage(MessageDTO messageDTO);
    
    // 받은 쪽지함
    List<MessageDTO> selectReceivedMessages(String receiverId);
    
    // 보낸 쪽지함
    List<MessageDTO> selectSentMessages(String senderId);
    
    // 쪽지 상세 조회
    MessageDTO selectMessageById(int messageId);
    
    // 쪽지 읽음 처리
    void updateMessageAsRead(int messageId);
    
    // 안 읽은 쪽지 개수
    int countUnreadMessages(String receiverId);
    
    // 쪽지 삭제 (받은 사람)
    void updateDeletedByReceiver(int messageId);
    
    // 쪽지 삭제 (보낸 사람)
    void updateDeletedBySender(int messageId);
    
    // 실제 삭제
    void deleteMessage(int messageId);
    
    // 양쪽 모두 삭제했는지 확인
    MessageDTO checkBothDeleted(int messageId);
}
