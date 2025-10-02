package com.green.backend_plant_comunity.category.mapper;

import com.green.backend_plant_comunity.category.dto.CategoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

   //카테고리 목록 조회
   List<CategoryDTO> getCategoryList();
}
