package com.green.backend_plant_comunity.board.controller;

import com.green.backend_plant_comunity.board.dto.BoardDTO;
import com.green.backend_plant_comunity.board.dto.BoardImgDTO;
import com.green.backend_plant_comunity.board.service.BoardService;
import com.green.backend_plant_comunity.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
   private final BoardService boardService;

   //게시글 등록할때 url 미리등록
   @PostMapping("/upload/img")
   public ResponseEntity<?> uploadImg(@RequestParam("img") List<MultipartFile> imgs){
      try {
         if (imgs == null || imgs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미지가 없습니다.");
         }
         List<BoardImgDTO> dtoList = FileUploadUtil.fileUpload(imgs);
         List<String> imageUrl = dtoList.stream()
                 .map(img -> "http://192.168.30.97:8080/upload/" + img.getAttachedImgName())
                 .collect(Collectors.toList());
         for(BoardImgDTO dto : dtoList){
            dto.setImgUrl("http://192.168.30.97:8080/upload/" + dto.getAttachedImgName());
         }
         boardService.insertUrl(dtoList);
         return ResponseEntity.ok(imageUrl);
      } catch (Exception e) {
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업로드 중 오류남");
      }
   }

   // 게시글 등록
   @PostMapping("")
   public ResponseEntity<?> writeBoard(@RequestBody BoardDTO boardDTO) {
      try {
         boardService.writeBoard(boardDTO);
         // boardDTO에 자동으로 생성된 boardNum이 담김
         return ResponseEntity.status(HttpStatus.OK).body(boardDTO.getBoardNum());
      } catch (Exception e) {
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 등록 실패");
      }
   }

   @GetMapping("/{memId}")
   //마이팜 게시글 조회 api
   public ResponseEntity<?> getMyFarmCommunity(@PathVariable ("memId") String memId ){
      try {
         List<BoardDTO> boards = boardService.getMyFarmCommunity(memId);
         return ResponseEntity.status(HttpStatus.OK).body(boards);
      } catch (Exception e) {
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("조회중 오류남");
      }
   }

   @GetMapping("")
   //홈 화면 인기글 조회
   public ResponseEntity<?> getPopularWriting(){
      try {
         List<BoardDTO> boards = boardService.getPopularWriting();
         return ResponseEntity.status(HttpStatus.OK).body(boards);
      } catch (Exception e) {
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("조회중 오류남");
      }
   }

   //전체 게시글 조회api
   @GetMapping("/boardList")
   public ResponseEntity<?> getAllBoardList(){
      try {
         List<BoardDTO> boards = boardService.getAllBoardList();
         return ResponseEntity.status(HttpStatus.OK).body(boards);
      } catch (Exception e) {
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("조회중 오류남");
      }
   }

   // admin 페이지 단일 게시글 삭제
   @DeleteMapping("/{boardNum}")
   public ResponseEntity<?> deleteBoardByAdmin(@PathVariable("boardNum") int boardNum) {
      try {
         int result = boardService.deleteBoardByAdmin(boardNum);
         if (result > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("게시글이 삭제되었습니다.");
         } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시글을 찾을 수 없습니다.");
         }
      } catch (Exception e) {
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제중 오류남");
      }
   }

   //게시글 목록 조회
   @GetMapping("/boardList-paging")
   public ResponseEntity<?> getBoardList(BoardDTO boardDTO){
      try {
         System.out.println(boardDTO);
         int totalCnt = boardService.getTotalBoardCnt(boardDTO);
         boardDTO.setTotalDataCnt(totalCnt);
         boardDTO.setPageInfo();
         Map<String, Object> map = new HashMap<>();
         map.put("boardList", boardService.getBoardList(boardDTO));
         map.put("boardDTO", boardDTO);
         return ResponseEntity.status(HttpStatus.OK).body(map);
      }catch (Exception e){
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
      }
   }

   //게시글 상세 조회
   @GetMapping("/boardDetail/{boardNum}")
   public ResponseEntity<?> getBoardDetail(@PathVariable("boardNum") int boardNum){
      try {
         boardService.updateCnt(boardNum);
         BoardDTO boardDTO = boardService.getBoardDetail(boardNum);
         if (boardDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시글을 찾을 수 없습니다.");
         }
         return ResponseEntity.status(HttpStatus.OK).body(boardDTO);
      }catch (Exception e){
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("조회중 오류남");
      }
   }

   //게시글 삭제
   @DeleteMapping("/boardDetail/{boardNum}")
   public ResponseEntity<?> deleteBoard(@PathVariable int boardNum){
      try {
         boardService.deleteBoard(boardNum);
         return ResponseEntity.status(HttpStatus.OK).body("게시글이 삭제되었습니다.");
      }catch (Exception e){
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제중 오류남");
      }
   }

   //게시글 수정
   @PutMapping("/boardDetail/{boardNum}")
   public ResponseEntity<?> updateBoard(@PathVariable int boardNum, @RequestBody BoardDTO boardDTO){
      try {
         boardDTO.setBoardNum(boardNum);
         boardService.updateBoard(boardDTO);
         return ResponseEntity.status(HttpStatus.OK).body("게시글이 수정되었습니다.");
      }catch (Exception e){
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수정중 오류남");
      }
   }
}