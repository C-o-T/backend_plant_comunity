package com.green.backend_plant_comunity.follow.controller;

import com.green.backend_plant_comunity.follow.dto.FollowDTO;
import com.green.backend_plant_comunity.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {
    
    private final FollowService followService;
    
    // 팔로우하기
    @PostMapping("/{followerId}/{followingId}")
    public ResponseEntity<String> follow(
            @PathVariable String followerId,
            @PathVariable String followingId) {
        followService.follow(followerId, followingId);
        return ResponseEntity.ok("팔로우 성공");
    }
    
    // 언팔로우하기
    @DeleteMapping("/{followerId}/{followingId}")
    public ResponseEntity<String> unfollow(
            @PathVariable String followerId,
            @PathVariable String followingId) {
        followService.unfollow(followerId, followingId);
        return ResponseEntity.ok("언팔로우 성공");
    }
    
    // 팔로우 여부 확인
    @GetMapping("/check/{followerId}/{followingId}")
    public ResponseEntity<Map<String, Boolean>> isFollowing(
            @PathVariable String followerId,
            @PathVariable String followingId) {
        boolean isFollowing = followService.isFollowing(followerId, followingId);
        Map<String, Boolean> result = new HashMap<>();
        result.put("isFollowing", isFollowing);
        return ResponseEntity.ok(result);
    }
    
    // 팔로잉 목록
    @GetMapping("/following/{memId}")
    public ResponseEntity<List<FollowDTO>> getFollowingList(@PathVariable String memId) {
        List<FollowDTO> followingList = followService.getFollowingList(memId);
        return ResponseEntity.ok(followingList);
    }
    
    // 팔로워 목록
    @GetMapping("/follower/{memId}")
    public ResponseEntity<List<FollowDTO>> getFollowerList(@PathVariable String memId) {
        List<FollowDTO> followerList = followService.getFollowerList(memId);
        return ResponseEntity.ok(followerList);
    }
    
    // 팔로잉/팔로워 수
    @GetMapping("/count/{memId}")
    public ResponseEntity<Map<String, Integer>> getFollowCount(@PathVariable String memId) {
        int followingCount = followService.getFollowingCount(memId);
        int followerCount = followService.getFollowerCount(memId);
        
        Map<String, Integer> result = new HashMap<>();
        result.put("followingCount", followingCount);
        result.put("followerCount", followerCount);
        return ResponseEntity.ok(result);
    }
    
    // 맞팔 목록
    @GetMapping("/mutual/{memId}")
    public ResponseEntity<List<FollowDTO>> getMutualFollowList(@PathVariable String memId) {
        List<FollowDTO> mutualList = followService.getMutualFollowList(memId);
        return ResponseEntity.ok(mutualList);
    }
}
