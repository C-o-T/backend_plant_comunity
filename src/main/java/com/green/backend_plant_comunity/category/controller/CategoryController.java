package com.green.backend_plant_comunity.category.controller;

import com.green.backend_plant_comunity.category.dto.CategoryDTO;
import com.green.backend_plant_comunity.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
   private final CategoryService categoryService;

   //카테고리 목록 조회
   @GetMapping("")
   public ResponseEntity<?> getCategoryList(){
      try {
         List<CategoryDTO> categoryList = categoryService.getCategoryList();
         return ResponseEntity.status(HttpStatus.OK).body(categoryList);
      }catch (Exception e){
         e.printStackTrace();
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("조회중 오류남");
      }
   }
}
