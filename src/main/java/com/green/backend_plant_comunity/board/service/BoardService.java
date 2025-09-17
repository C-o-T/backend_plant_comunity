package com.green.backend_plant_comunity.board.service;

import com.green.backend_plant_comunity.board.dto.BoardDTO;
import com.green.backend_plant_comunity.board.dto.BoardImgDTO;
import com.green.backend_plant_comunity.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
   private final BoardMapper boardMapper;

   @Transactional(rollbackFor = Exception.class)
   public void writeBoard(List<BoardImgDTO> imgList, BoardDTO boardDTO){
      int nextBoardNum = boardMapper.getNextBoardNum();

      boardDTO.setBoardNum(nextBoardNum);
      boardMapper.writeBoard(boardDTO);
      if(imgList != null && !imgList.isEmpty()) {
         for (BoardImgDTO dto : imgList) {
            dto.setBoardNum(nextBoardNum);
         }
         boardMapper.writeImg(imgList);
      }
   }

   //마이팜 게시글 조회
   public List<BoardDTO> getMyFarmCommunity(String memId){
      return boardMapper.getMyFarmCommunity(memId);
   }

   //홈 화면 인기글 조회
   public List<BoardDTO> getPopularWriting(){
      return boardMapper.getPopularWriting();
   }
}
