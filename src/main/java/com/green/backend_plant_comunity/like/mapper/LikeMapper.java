package com.green.backend_plant_comunity.like.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {

   //좋아요 존재 확인
   boolean existsLike(@Param("boardNum") int boardNum, @Param("memId") String memId);

   //좋아요 추가
   void insertLike(@Param("boardNum") int boardNum, @Param("memId") String memId);

   //좋아요 삭제
   void deleteLike(@Param("boardNum") int boardNum, @Param("memId") String memId);
}
