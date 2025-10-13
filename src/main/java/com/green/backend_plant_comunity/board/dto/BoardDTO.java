package com.green.backend_plant_comunity.board.dto;

import com.green.backend_plant_comunity.category.dto.CategoryDTO;
import com.green.backend_plant_comunity.page.dto.PageDTO;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class BoardDTO extends PageDTO {
   private int boardNum;
   private String title;
   private String content;
   private String memId;
   private int cateNum;
   private int readCnt;
   private int likeCnt;
   private int dislikeCnt;
   private int commentCnt;  // ← 추가!
   private LocalDateTime createDate;
   private String searchType;
   private String searchKeyword;

   private CategoryDTO categoryDTO;
   private BoardImgDTO imgList;

}
