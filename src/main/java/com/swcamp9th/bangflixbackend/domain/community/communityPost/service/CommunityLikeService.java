package com.swcamp9th.bangflixbackend.domain.community.communityPost.service;

import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityLikeCreateDTO;

public interface CommunityLikeService {

    void addLike(String loginId, CommunityLikeCreateDTO newLike);
}
