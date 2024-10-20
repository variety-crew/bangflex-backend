package com.swcamp9th.bangflixbackend.domain.communityPost.service;

import com.swcamp9th.bangflixbackend.domain.communityPost.dto.CommunityLikeCountDTO;
import com.swcamp9th.bangflixbackend.domain.communityPost.dto.CommunityLikeCreateDTO;

public interface CommunityLikeService {

    void addLike(String loginId, CommunityLikeCreateDTO newLike);

    CommunityLikeCountDTO countLike(int communityPostCode);
}
