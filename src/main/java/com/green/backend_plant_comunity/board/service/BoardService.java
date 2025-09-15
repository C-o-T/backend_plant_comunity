package com.green.backend_plant_comunity.board.service;

import com.green.backend_plant_comunity.board.dto.BoardImgDTO;
import com.green.backend_plant_comunity.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
   private final BoardMapper boardMapper;

   public void writeImg(List<BoardImgDTO> imgList){
      boardMapper.writeImg(imgList);
   }
}
