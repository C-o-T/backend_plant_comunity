package com.green.backend_plant_comunity.comment.controller;

import com.green.backend_plant_comunity.comment.dto.CommentDTO;
import com.green.backend_plant_comunity.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
   private final CommentService commentService;

   //댓글 등록
   @PostMapping("")
   public ResponseEntity<?> writeComment(@RequestBody CommentDTO commentDTO){
      try {
         commentService.writeComment(commentDTO);
         return ResponseEntity.status(HttpStatus.OK).body("댓글이 등록되었습니다.");
      }catch (Exception e){
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("등록중 오류남");
      }
   }

   //댓글 조회 (게시글별)
   @GetMapping("/{boardNum}")
   public ResponseEntity<?> getComments(@PathVariable int boardNum){
      try {
         List<CommentDTO> comments = commentService.getCommentsByBoardNum(boardNum);
         return ResponseEntity.status(HttpStatus.OK).body(comments);
      }catch (Exception e){
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("조회중 오류남");
      }
   }

   //댓글 개수 조회
   @GetMapping("/{boardNum}/count")
   public ResponseEntity<?> getCommentCount(@PathVariable int boardNum){
      try {
         int count = commentService.getCommentCount(boardNum);
         return ResponseEntity.status(HttpStatus.OK).body(count);
      }catch (Exception e){
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("조회중 오류남");
      }
   }

   //댓글 수정
   @PutMapping("/{commentNum}")
   public ResponseEntity<?> updateComment(@PathVariable int commentNum, @RequestBody CommentDTO commentDTO){
      try {
         commentDTO.setCommentNum(commentNum);
         commentService.updateComment(commentDTO);
         return ResponseEntity.status(HttpStatus.OK).body("댓글이 수정되었습니다.");
      }catch (Exception e){
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수정중 오류남");
      }
   }

   //댓글 삭제
   @DeleteMapping("/{commentNum}")
   public ResponseEntity<?> deleteComment(@PathVariable int commentNum){
      try {
         commentService.deleteComment(commentNum);
         return ResponseEntity.status(HttpStatus.OK).body("댓글이 삭제되었습니다.");
      }catch (Exception e){
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제중 오류남");
      }
   }
}
