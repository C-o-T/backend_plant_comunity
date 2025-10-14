package com.green.backend_plant_comunity.comment.service;

import com.green.backend_plant_comunity.comment.dto.CommentDTO;
import com.green.backend_plant_comunity.comment.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
   private final CommentMapper commentMapper;

   //댓글 등록
   @Transactional(rollbackFor = Exception.class)
   public void writeComment(CommentDTO commentDTO){
      commentMapper.insertComment(commentDTO);
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
