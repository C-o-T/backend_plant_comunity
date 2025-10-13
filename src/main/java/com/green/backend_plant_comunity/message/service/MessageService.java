package com.green.backend_plant_comunity.message.service;

import com.green.backend_plant_comunity.member.service.MemberService;
import com.green.backend_plant_comunity.member.dto.MemberDTO;
import com.green.backend_plant_comunity.message.dto.MessageDTO;
import com.green.backend_plant_comunity.message.dto.MessageSendRequestDTO;
import com.green.backend_plant_comunity.message.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageMapper messageMapper;
    private final MemberService memberService;

    // 쪽지 보내기 (단일/다중/전체 지원)
    public void sendMessage(String senderId, MessageSendRequestDTO request) {
        List<String> targetReceivers = new ArrayList<>();

        // 1. 전체 발송인 경우
        if (request.isSendToAll()) {
            List<MemberDTO> allMembers = memberService.getAllMembers();
            for (MemberDTO member : allMembers) {
                // 자기 자신은 제외
                if (!member.getMemId().equals(senderId)) {
                    targetReceivers.add(member.getMemId());
                }
            }
        }
        // 2. 다중 발송인 경우
        else if (request.getReceiverIds() != null && !request.getReceiverIds().isEmpty()) {
            targetReceivers.addAll(request.getReceiverIds());
        }
        // 3. 단일 발송인 경우 (하위 호환성)
        else if (request.getReceiverId() != null && !request.getReceiverId().trim().isEmpty()) {
            targetReceivers.add(request.getReceiverId());
        }

        // 각 수신자에게 쪽지 발송
        for (String receiverId : targetReceivers) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setSenderId(senderId);
            messageDTO.setReceiverId(receiverId);
            messageDTO.setTitle(request.getTitle());
            messageDTO.setContent(request.getContent());
            messageMapper.insertMessage(messageDTO);
        }
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
    public void deleteMessage(int messageId, String memberId, String deleteType) {
        MessageDTO message = messageMapper.selectMessageById(messageId);

        // deleteType이 명시적으로 전달된 경우 우선 사용
        if ("sender".equals(deleteType)) {
            messageMapper.updateDeletedBySender(messageId);
        } else if ("receiver".equals(deleteType)) {
            messageMapper.updateDeletedByReceiver(messageId);
        } else {
            // deleteType이 없는 경우 기존 로직 사용
            if (message.getSenderId().equals(memberId)) {
                messageMapper.updateDeletedBySender(messageId);
            } else {
                messageMapper.updateDeletedByReceiver(messageId);
            }
        }

        // 양쪽 모두 삭제했으면 물리적 삭제
        MessageDTO updated = messageMapper.checkBothDeleted(messageId);
        if (updated.isDeletedBySender() && updated.isDeletedByReceiver()) {
            messageMapper.deleteMessage(messageId);
        }
    }
}
