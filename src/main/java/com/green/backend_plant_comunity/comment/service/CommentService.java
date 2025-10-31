package com.green.backend_plant_comunity.comment.service;

import com.green.backend_plant_comunity.board.dto.BoardDTO;
import com.green.backend_plant_comunity.board.mapper.BoardMapper;
import com.green.backend_plant_comunity.comment.dto.CommentDTO;
import com.green.backend_plant_comunity.comment.mapper.CommentMapper;
import com.green.backend_plant_comunity.member.dto.MemberDTO;
import com.green.backend_plant_comunity.member.mapper.MemberMapper;
import com.green.backend_plant_comunity.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentService {
   private final CommentMapper commentMapper;
   private final BoardMapper boardMapper;
   private final MemberMapper memberMapper;
   private final NotificationService notificationService;

   //댓글 등록
   @Transactional(rollbackFor = Exception.class)
   public void writeComment(CommentDTO commentDTO){
      commentMapper.insertComment(commentDTO);

      // 댓글 작성 시 게시글 작성자에게 알림 전송
      sendCommentNotification(commentDTO);
   }

   // 댓글 알림 전송
   private void sendCommentNotification(CommentDTO commentDTO) {
      try {
         // 1. 게시글 정보 조회
         BoardDTO board = boardMapper.getBoardDetail(commentDTO.getBoardNum());
         if (board == null) {
            return;
         }

         // 2. 게시글 작성자 정보 조회
         MemberDTO boardWriter = memberMapper.getMemberDetail(board.getMemId());
         if (boardWriter == null || boardWriter.getPushToken() == null) {
            return;
         }

         // 3. 댓글 작성자가 게시글 작성자와 같으면 알림 보내지 않음
         if (commentDTO.getMemId().equals(board.getMemId())) {
            return;
         }

         // 4. 알림 전송
         String title = "새 댓글 알림";
         String body = commentDTO.getMemId() + "님이 회원님의 게시글에 댓글을 남겼습니다.";

         Map<String, Object> data = new HashMap<>();
         data.put("boardNum", commentDTO.getBoardNum());
         data.put("commentNum", commentDTO.getCommentNum());

         notificationService.sendPushNotification(
            boardWriter.getPushToken(),
            title,
            body,
            data
         );

      } catch (Exception e) {
         // 알림 전송 실패해도 댓글 작성은 성공으로 처리
         System.err.println("댓글 알림 전송 실패: " + e.getMessage());
      }
   }

   //게시글의 댓글 조회 (대댓글 포함)
   public List<CommentDTO> getCommentsByBoardNum(int boardNum){
      // 1. 일반 댓글 조회
      List<CommentDTO> comments = commentMapper.getCommentsByBoardNum(boardNum);
      
      // 2. 각 댓글의 대댓글 조회
      for(CommentDTO comment : comments) {
         List<CommentDTO> replies = commentMapper.getRepliesByParentNum(comment.getCommentNum());
         comment.setReplies(replies);  // 대댓글 목록 세팅
      }
      
      return comments;
   }

   //게시글의 댓글 총 개수
   public int getCommentCount(int boardNum){
      return commentMapper.getCommentCount(boardNum);
   }

   //댓글 수정
   @Transactional(rollbackFor = Exception.class)
   public void updateComment(CommentDTO commentDTO){
      commentMapper.updateComment(commentDTO);
   }

   //댓글 삭제 (대댓글도 함께 삭제)
   @Transactional(rollbackFor = Exception.class)
   public void deleteComment(int commentNum){
      commentMapper.deleteComment(commentNum);
   }
}
