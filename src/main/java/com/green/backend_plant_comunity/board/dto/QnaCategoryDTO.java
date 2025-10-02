package com.green.backend_plant_comunity.board.dto;

import lombok.Data;

@Data
public class QnaCategoryDTO {
  private int cateNum;
  private String cateName;
  private int cateOrder;
  private boolean isActive;
}
