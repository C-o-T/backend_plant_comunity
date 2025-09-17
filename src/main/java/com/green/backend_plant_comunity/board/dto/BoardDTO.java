package com.green.backend_plant_comunity.board.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class BoardDTO {
   private int boardNum;
   private String title;
   private String content;
   private String memId;
   private int herbNum;
   private int readCnt;
   private int likeCnt;
   private int dislikeCnt;
   private LocalDateTime createDate;
}
