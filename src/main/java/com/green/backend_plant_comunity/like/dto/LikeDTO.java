package com.green.backend_plant_comunity.like.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LikeDTO {
   private int likeNum;
   private int boardNum;
   private String memId;
   private LocalDateTime createDate;
}
