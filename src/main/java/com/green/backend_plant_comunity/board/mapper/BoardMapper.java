package com.green.backend_plant_comunity.board.mapper;

import com.green.backend_plant_comunity.board.dto.BoardImgDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
   //글쓰기 이미지 등록
   public void writeImg(List<BoardImgDTO> imgList);
}
