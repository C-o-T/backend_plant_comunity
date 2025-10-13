package com.green.backend_plant_comunity.board.mapper;

import com.green.backend_plant_comunity.board.dto.BoardDTO;
import com.green.backend_plant_comunity.board.dto.BoardImgDTO;
import com.green.backend_plant_comunity.page.dto.PageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

   //글쓰기 등록
   public void writeBoard(BoardDTO boardDTO);

   //BOARD 테이블에 데이터 삽입 시 저장되는 BOARD_NUM을 조회하는 쿼리
   public int getNextBoardNum();

   //마이팜 게시글 조회
   public List<BoardDTO> getMyFarmCommunity(String memId);

   //홈 화면 인기글 조회
   public List<BoardDTO> getPopularWriting();

   //전체 게시글 조회
   public List<BoardDTO> getAllBoardList();

   //url 데이터베이스에 삽입
   public void insertUrl(List<BoardImgDTO> imgList);

   //글쓸때 이미지 글번호와 일치 시키고 이미지 사용 확인
   public void updateImg(BoardImgDTO boardImgDTO);

   public List<BoardImgDTO> getUnusedImg();

   public int deleteUnusedImg(int imgNum);

   //admin 페이지 게시글 삭제 기능
   public int deleteBoardByAdmin(int boardNum);

   //게시글 목록 조회
   public List<BoardDTO> getBoardList(BoardDTO boardDTO);

   //글 총개수
   public int getTotalBoardCnt(BoardDTO boardDTO);

   //게시글 상세보기
   public BoardDTO getBoardDetail(int boardNum);

   //게시글 삭제
   public void deleteBoard(int boardNum);

   //게시글 수정
   public void updateBoard(BoardDTO boardDTO);

   //게시글의 모든 이미지를 USED=FALSE로 변경
   public void markImagesAsUnused(int boardNum);

   //좋아요 개수 증가
   public void increaseLikeCnt(int boardNum);

   //좋아요 개수 감소
   public void decreaseLikeCnt(int boardNum);

   //조회수 증가
   public void updateCnt(int boardNum);
}
