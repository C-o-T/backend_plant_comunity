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
      
      // INSERT 수행 - useGeneratedKeys로 자동 생성된 키가 boardDTO.boardNum에 설정됨
      boardMapper.writeBoard(boardDTO);
      int generatedBoardNum = boardDTO.getBoardNum();  // AUTO_INCREMENT로 생성된 실제 번호
      
      //게시글 내용 가져오기
      String contentHtml = boardDTO.getContent();

      System.out.println(contentHtml);
      System.out.println("생성된 게시글 번호: " + generatedBoardNum);

      //글내용에서 img 태그의 src 속성만 추출
      List<String> imgUrls = HtmlImageParser.extractImageUrls(contentHtml);

      System.out.println(imgUrls.size());

      for(String url : imgUrls) {
         System.out.println("이미지 등록" + url);
         boardImgDTO.setImgUrl(url);
         boardImgDTO.setBoardNum(generatedBoardNum);  // 실제 생성된 번호 사용
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
      List<BoardDTO> list = boardMapper.getBoardList(boardDTO);
      
      // ✅ 검색 결과가 없어도 빈 리스트 반환
      // 프론트엔드에서 "검색 결과 없음" 처리
      
      return list;
   }

   //글 총개수
   public int getTotalBoardCnt(BoardDTO boardDTO){
      int totalCnt = boardMapper.getTotalBoardCnt(boardDTO);
      
      // ✅ 검색 결과가 0이어도 정상 - 예외 제거
      // 프론트엔드에서 "검색 결과 없음" 처리
      
      return totalCnt;
   }

   //게시글 상세보기
   public BoardDTO getBoardDetail(int boardNum){
      return boardMapper.getBoardDetail(boardNum);
   }

   //게시글 삭제
   @Transactional(rollbackFor = Exception.class)
   public void deleteBoard(int boardNum){
      // 1. 해당 게시글의 이미지를 USED = FALSE로 변경
      boardMapper.markImagesAsUnused(boardNum);
      
      // 2. 게시글 삭제
      boardMapper.deleteBoard(boardNum);
      
      // 3. 이미지는 스케줄러가 자동 정리
   }

   //게시글 수정
   @Transactional(rollbackFor = Exception.class)
   public void updateBoard(BoardDTO boardDTO){
      BoardImgDTO boardImgDTO = new BoardImgDTO();
      
      // 1. 게시글 내용 업데이트
      boardMapper.updateBoard(boardDTO);

      // 2. 기존 이미지를 모두 USED = FALSE로 변경
      boardMapper.markImagesAsUnused(boardDTO.getBoardNum());

      // 3. 새로운 HTML에서 이미지 추출
      String contentHtml = boardDTO.getContent();
      List<String> imgUrls = HtmlImageParser.extractImageUrls(contentHtml);

      // 4. 사용된 이미지만 USED = TRUE로 변경
      for(String url : imgUrls) {
         System.out.println("수정 후 사용 이미지: " + url);
         boardImgDTO.setImgUrl(url);
         boardImgDTO.setBoardNum(boardDTO.getBoardNum());
         boardImgDTO.setUsed(true);
         boardMapper.updateImg(boardImgDTO);
      }
      // 5. 미사용 이미지는 스케줄러가 자동 정리
   }

   //조회수 증가
   @Transactional(rollbackFor = Exception.class)
   public void updateCnt(int boardNum){
      System.out.println("조회수 증가 요청: boardNum=" + boardNum);
      boardMapper.updateCnt(boardNum);
      System.out.println("조회수 증가 완료");
   }
}
