package com.green.backend_plant_comunity.like.controller;

import com.green.backend_plant_comunity.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {
   private final LikeService likeService;

   //좋아요 토글 (누르기/취소)
   @PostMapping("/{boardNum}")
   public ResponseEntity<?> toggleLike(@PathVariable int boardNum, @RequestBody Map<String, String> body){
      try {
         String memId = body.get("memId");
         likeService.toggleLike(boardNum, memId);
         return ResponseEntity.status(HttpStatus.OK).body("처리되었습니다.");
      }catch (Exception e){
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("처리중 오류남");
      }
   }

   //좋아요 상태 확인
   @GetMapping("/{boardNum}/check")
   public ResponseEntity<?> checkLike(@PathVariable int boardNum, @RequestParam String memId){
      try {
         boolean isLiked = likeService.checkLike(boardNum, memId);
         return ResponseEntity.status(HttpStatus.OK).body(isLiked);
      }catch (Exception e){
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("조회중 오류남");
      }
   }
}
