package com.green.backend_plant_comunity.board.controller;

import com.green.backend_plant_comunity.board.dto.BoardDTO;
import com.green.backend_plant_comunity.board.dto.BoardImgDTO;
import com.green.backend_plant_comunity.board.service.BoardService;
import com.green.backend_plant_comunity.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
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
   //게시글 목록 조회
   @GetMapping("/boardList")
   public Map<String, Object> getBoardList(BoardDTO boardDTO){
      int totalCnt = boardService.getTotalBoardCnt();
      boardDTO.setTotalDataCnt(totalCnt);
      boardDTO.setPageInfo();
      Map<String, Object> map = new HashMap<>();
      map.put("boardList", boardService.getBoardList(boardDTO));
      map.put("boardDTO", boardDTO);
      return map;
   }
}
