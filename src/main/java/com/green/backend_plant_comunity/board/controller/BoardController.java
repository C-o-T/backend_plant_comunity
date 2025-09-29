package com.green.backend_plant_comunity.board.controller;

import com.green.backend_plant_comunity.board.dto.BoardDTO;
import com.green.backend_plant_comunity.board.dto.BoardImgDTO;
import com.green.backend_plant_comunity.board.service.BoardService;
import com.green.backend_plant_comunity.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
   private final BoardService boardService;

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

   @PostMapping("")
   public void writeBoard(@RequestBody BoardDTO boardDTO) {
      boardService.writeBoard(boardDTO);
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

<<<<<<< HEAD
   @GetMapping("/boardList")
   public List<BoardDTO> getBoardList(){
      return boardService.getBoardList();
=======
   // admin 페이지 단일 게시글 삭제
   @DeleteMapping("/{boardNum}")
   public int deleteBoardByAdmin(@PathVariable("boardNum") int boardNum) {
      return boardService.deleteBoardByAdmin(boardNum);
>>>>>>> fea/admin_member
   }
}
