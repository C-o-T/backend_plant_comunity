package com.green.backend_plant_comunity.follow.service;

import com.green.backend_plant_comunity.follow.dto.FollowDTO;
import com.green.backend_plant_comunity.follow.mapper.FollowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    
    private final FollowMapper followMapper;
    
    // 팔로우하기
    @Transactional
    public void follow(String followerId, String followingId) {
        if (followerId.equals(followingId)) {
            throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        }
        followMapper.insertFollow(followerId, followingId);
    }
    
    // 언팔로우하기
    @Transactional
    public void unfollow(String followerId, String followingId) {
        followMapper.deleteFollow(followerId, followingId);
    }
    
    // 팔로우 여부 확인
    public boolean isFollowing(String followerId, String followingId) {
        return followMapper.isFollowing(followerId, followingId);
    }
    
    // 팔로잉 목록
    public List<FollowDTO> getFollowingList(String memId) {
        return followMapper.getFollowingList(memId);
    }
    
    // 팔로워 목록
    public List<FollowDTO> getFollowerList(String memId) {
        return followMapper.getFollowerList(memId);
    }
    
    // 팔로잉 수
    public int getFollowingCount(String memId) {
        return followMapper.getFollowingCount(memId);
    }
    
    // 팔로워 수
    public int getFollowerCount(String memId) {
        return followMapper.getFollowerCount(memId);
    }
    
    // 맞팔 목록
    public List<FollowDTO> getMutualFollowList(String memId) {
        return followMapper.getMutualFollowList(memId);
    }
}
