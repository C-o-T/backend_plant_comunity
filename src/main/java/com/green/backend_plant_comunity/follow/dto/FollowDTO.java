package com.green.backend_plant_comunity.follow.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FollowDTO {
    private int followId;
    private String followerId;      // 팔로우하는 사람
    private String followingId;     // 팔로우당하는 사람
    private LocalDateTime followedAt;
    
    // 추가 정보 (JOIN용)
    private String followerName;
    private String followerEmail;
    private String followingName;
    private String followingEmail;
}
