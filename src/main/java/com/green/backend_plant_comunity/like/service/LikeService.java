package com.green.backend_plant_comunity.like.service;

import com.green.backend_plant_comunity.board.mapper.BoardMapper;
import com.green.backend_plant_comunity.like.mapper.LikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
   private final LikeMapper likeMapper;
   private final BoardMapper boardMapper;

   //좋아요 토글 (누르기/취소)
   @Transactional(rollbackFor = Exception.class)
   public void toggleLike(int boardNum, String memId) {
      // 이미 좋아요 눌렀는지 확인
      boolean exists = likeMapper.existsLike(boardNum, memId);
      
      if(exists) {
         // 좋아요 취소
         likeMapper.deleteLike(boardNum, memId);
         boardMapper.decreaseLikeCnt(boardNum);
      } else {
         // 좋아요 추가
         likeMapper.insertLike(boardNum, memId);
         boardMapper.increaseLikeCnt(boardNum);
      }
   }

   //좋아요 상태 확인
   public boolean checkLike(int boardNum, String memId) {
      return likeMapper.existsLike(boardNum, memId);
   }
}
