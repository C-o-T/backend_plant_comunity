package com.green.backend_plant_comunity.follow.mapper;

import com.green.backend_plant_comunity.follow.dto.FollowDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FollowMapper {
    
    // 팔로우하기
    void insertFollow(@Param("followerId") String followerId, 
                     @Param("followingId") String followingId);
    
    // 언팔로우하기
    void deleteFollow(@Param("followerId") String followerId, 
                     @Param("followingId") String followingId);
    
    // 팔로우 여부 확인
    boolean isFollowing(@Param("followerId") String followerId, 
                       @Param("followingId") String followingId);
    
    // 내가 팔로우한 사람 목록 (팔로잉)
    List<FollowDTO> getFollowingList(@Param("memId") String memId);
    
    // 나를 팔로우하는 사람 목록 (팔로워)
    List<FollowDTO> getFollowerList(@Param("memId") String memId);
    
    // 팔로잉 수
    int getFollowingCount(@Param("memId") String memId);
    
    // 팔로워 수
    int getFollowerCount(@Param("memId") String memId);
    
    // 맞팔 목록
    List<FollowDTO> getMutualFollowList(@Param("memId") String memId);
}
