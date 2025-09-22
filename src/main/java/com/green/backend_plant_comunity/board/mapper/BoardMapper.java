package com.green.backend_plant_comunity.board.mapper;

import com.green.backend_plant_comunity.board.dto.BoardDTO;
import com.green.backend_plant_comunity.board.dto.BoardImgDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    //글쓸때 파일에 등록된 이미지 데이터베이스에 저장
   public void writeImg(List<BoardImgDTO> imgList);

   //글쓰기 등록
   public void writeBoard(BoardDTO boardDTO);

   //BOARD 테이블에 데이터 삽입 시 저장되는 BOARD_NUM을 조회하는 쿼리
   public int getNextBoardNum();

   //마이팜 게시글 조회
   public List<BoardDTO> getMyFarmCommunity(String memId);

   //홈 화면 인기글 조회
   public List<BoardDTO> getPopularWriting();

   //url 데이터베이스에 삽입
   public void insertUrl(List<BoardImgDTO> boardImgDTO);
}
