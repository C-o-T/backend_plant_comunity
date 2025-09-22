package com.green.backend_plant_comunity.board.dto;

import lombok.Data;

@Data
public class BoardImgDTO {
   private int imgNum;
   private String originImgName;
   private String attachedImgName;
   private int boardNum;
   private String imgUrl;
   private boolean used;
}
