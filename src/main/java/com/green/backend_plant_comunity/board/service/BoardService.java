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
   public void writeBoard(BoardDTO boardDTO){
      int nextBoardNum = boardMapper.getNextBoardNum();

      boardDTO.setBoardNum(nextBoardNum);
      boardMapper.writeBoard(boardDTO);
   }

   //마이팜 게시글 조회
   public List<BoardDTO> getMyFarmCommunity(String memId){
      return boardMapper.getMyFarmCommunity(memId);
   }

   //홈 화면 인기글 조회
   public List<BoardDTO> getPopularWriting(){
      return boardMapper.getPopularWriting();
   }

   //url 데이터베이스에 삽입
   public void insertUrl(List<BoardImgDTO> imgList){
      boardMapper.insertUrl(imgList);
   }
}
