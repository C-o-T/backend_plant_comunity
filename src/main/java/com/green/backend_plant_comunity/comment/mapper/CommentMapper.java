package com.green.backend_plant_comunity.comment.mapper;

import com.green.backend_plant_comunity.comment.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

   //댓글 등록
   void insertComment(CommentDTO commentDTO);

   //게시글의 댓글 조회 (일반 댓글만)
   List<CommentDTO> getCommentsByBoardNum(int boardNum);

   //특정 댓글의 대댓글 조회
   List<CommentDTO> getRepliesByParentNum(int parentCommentNum);

   //게시글의 댓글 총 개수 (댓글 + 대댓글)
   int getCommentCount(int boardNum);

   //댓글 수정
   void updateComment(CommentDTO commentDTO);

   //댓글 삭제
   void deleteComment(int commentNum);
}
