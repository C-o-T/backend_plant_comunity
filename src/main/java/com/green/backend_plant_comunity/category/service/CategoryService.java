package com.green.backend_plant_comunity.category.service;

import com.green.backend_plant_comunity.category.dto.CategoryDTO;
import com.green.backend_plant_comunity.category.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
   private final CategoryMapper categoryMapper;

   //카테고리 목록 조회
   public List<CategoryDTO> getCategoryList(){
      return categoryMapper.getCategoryList();
   }
}
