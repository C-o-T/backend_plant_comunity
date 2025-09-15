package com.green.backend_plant_comunity.board.controller;

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
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
   private final BoardService boardService;
   @PostMapping("")
   public List<String> writeImg(@RequestParam("img")List<MultipartFile> imgs){
      List<String> urls = new ArrayList<>();

      for (MultipartFile img : imgs) {
         // 파일 저장 및 DTO 생성
         BoardImgDTO dto = FileUploadUtil.fileUpload(img);

         // 이미지가 저장된 URL (예: /upload/파일명)
         String url = "/upload/" + dto.getAttachedImgName();

         urls.add(url);
      }
   return urls;
   }
}
