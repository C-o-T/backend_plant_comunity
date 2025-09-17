package com.green.backend_plant_comunity.board.controller;

import com.green.backend_plant_comunity.board.dto.BoardDTO;
import com.green.backend_plant_comunity.board.dto.BoardImgDTO;
import com.green.backend_plant_comunity.board.service.BoardService;
import com.green.backend_plant_comunity.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
   private final BoardService boardService;

   @PostMapping("")
   public void writeImg(@RequestParam(name = "img", required = false) List<MultipartFile> imgs, BoardDTO boardDTO) {
      if(imgs == null){
         imgs = new ArrayList<>();
      }
      //Arrays.asList(imgs).stream().forEach(img -> System.out.println(img.getSize()));


      List<BoardImgDTO> dtoList = FileUploadUtil.fileUpload(imgs);

      boardService.writeBoard(dtoList,boardDTO);
   }


}
