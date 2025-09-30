package com.green.backend_plant_comunity.board.service;

import com.green.backend_plant_comunity.board.dto.BoardDTO;
import com.green.backend_plant_comunity.board.dto.BoardImgDTO;
import com.green.backend_plant_comunity.board.mapper.BoardMapper;
import com.green.backend_plant_comunity.util.HtmlImageParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
   private final BoardMapper boardMapper;
   //게시글 등록
   @Transactional(rollbackFor = Exception.class)
   public void writeBoard(BoardDTO boardDTO){
      BoardImgDTO boardImgDTO = new BoardImgDTO();
      int nextBoardNum = boardMapper.getNextBoardNum();
      boardDTO.setBoardNum(nextBoardNum);
      boardMapper.writeBoard(boardDTO);

      //게시글 내용 가져오기
      String contentHtml = boardDTO.getContent();

      System.out.println(contentHtml);

      //글내용에서 img 태그의 src 속성만 추출
      List<String> imgUrls = HtmlImageParser.extractImageUrls(contentHtml);

      System.out.println(imgUrls.size());

      for(String url : imgUrls) {
         System.out.println("이미지 등록" + url);
         boardImgDTO.setImgUrl(url);
         boardImgDTO.setBoardNum(nextBoardNum);
         boardImgDTO.setUsed(true);
         boardMapper.updateImg(boardImgDTO);
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

   //전체 게시글 조회
   public List<BoardDTO> getAllBoardList(){
      return boardMapper.getAllBoardList();
   }

   //url 데이터베이스에 삽입
   public void insertUrl(List<BoardImgDTO> imgList){
      boardMapper.insertUrl(imgList);
   }

   // admin 페이지 단일 게시글 삭제
   public int deleteBoardByAdmin(int boardNum) {
      return boardMapper.deleteBoardByAdmin(boardNum);
   }
   //게시글 목록 조회
   public List<BoardDTO> getBoardList(BoardDTO boardDTO){
      return boardMapper.getBoardList(boardDTO);
   }

   //글 총개수
   public int getTotalBoardCnt(){
      return boardMapper.getTotalBoardCnt();
   }

   //게시글 상세보기
   public BoardDTO getBoardDetail(int boardNum){
      return boardMapper.getBoardDetail(boardNum);
   }
}
