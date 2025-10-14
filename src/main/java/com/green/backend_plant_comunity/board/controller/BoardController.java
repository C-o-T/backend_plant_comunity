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
      List<BoardImgDTO> dtoList = FileUploadUtil.fileUpload(imgs);
      List<String> imageUrl = dtoList.stream().map(img -> "http://localhost:8080/upload/" + img.getAttachedImgName()).collect(Collectors.toList());
      for(BoardImgDTO dto : dtoList){
         dto.setImgUrl("http://localhost:8080/upload/" + dto.getAttachedImgName());
      }
      boardService.insertUrl(dtoList);
      return ResponseEntity.ok(imageUrl);
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
   public List<BoardDTO> getMyFarmCommunity(@PathVariable ("memId") String memId ){
      return boardService.getMyFarmCommunity(memId);
   }

   @GetMapping("")
   //홈 화면 인기글 조회
   public List<BoardDTO> getPopularWriting(){
      return boardService.getPopularWriting();
   }

   //전체 게시글 조회api
   @GetMapping("/boardList")
   public List<BoardDTO> getAllBoardList(){
      return boardService.getAllBoardList();
   }

   // admin 페이지 단일 게시글 삭제
   @DeleteMapping("/{boardNum}")
   public int deleteBoardByAdmin(@PathVariable("boardNum") int boardNum) {
      return boardService.deleteBoardByAdmin(boardNum);
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
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
      }
   }

   //게시글 상세 조회
   @GetMapping("/boardDetail/{boardNum}")
   public ResponseEntity<?> getBoardDetail(@PathVariable("boardNum") int boardNum){
      try {
         boardService.updateCnt(boardNum);
         BoardDTO boardDTO = boardService.getBoardDetail(boardNum);
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
