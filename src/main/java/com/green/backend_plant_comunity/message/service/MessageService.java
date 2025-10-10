package com.green.backend_plant_comunity.message.service;

import com.green.backend_plant_comunity.message.dto.MessageDTO;
import com.green.backend_plant_comunity.message.dto.MessageSendRequestDTO;
import com.green.backend_plant_comunity.message.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageMapper messageMapper;

    // 쪽지 보내기
    public void sendMessage(String senderId, MessageSendRequestDTO request) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setSenderId(senderId);
        messageDTO.setReceiverId(request.getReceiverId());
        messageDTO.setTitle(request.getTitle());
        messageDTO.setContent(request.getContent());
        messageMapper.insertMessage(messageDTO);
    }

    
    // 받은 쪽지함
    public List<MessageDTO> getReceivedMessages(String memberId) {
        return messageMapper.selectReceivedMessages(memberId);
    }
    
    // 보낸 쪽지함
    public List<MessageDTO> getSentMessages(String memberId) {
        return messageMapper.selectSentMessages(memberId);
    }
    
    // 쪽지 읽기
    public MessageDTO readMessage(int messageId) {
        messageMapper.updateMessageAsRead(messageId);
        return messageMapper.selectMessageById(messageId);
    }
    
    // 안 읽은 쪽지 개수
    public int getUnreadCount(String memberId) {
        return messageMapper.countUnreadMessages(memberId);
    }
    
    // 쪽지 삭제
    public void deleteMessage(int messageId, String memberId) {
        MessageDTO message = messageMapper.selectMessageById(messageId);
        
        if (message.getSenderId().equals(memberId)) {
            messageMapper.updateDeletedBySender(messageId);
        } else {
            messageMapper.updateDeletedByReceiver(messageId);
        }
        
        MessageDTO updated = messageMapper.checkBothDeleted(messageId);
        if (updated.isDeletedBySender() && updated.isDeletedByReceiver()) {
            messageMapper.deleteMessage(messageId);
        }
    }
}
