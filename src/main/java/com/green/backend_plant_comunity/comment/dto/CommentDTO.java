package com.green.backend_plant_comunity.comment.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentDTO {
   private int commentNum;
   private int boardNum;
   private String memId;
   private String content;
   private Integer parentCommentNum;  // ← int에서 Integer로 변경! null 값 받을려고
   private LocalDateTime createDate;
   
   // 대댓글 목록 (조회용)
   private List<CommentDTO> replies;
}
