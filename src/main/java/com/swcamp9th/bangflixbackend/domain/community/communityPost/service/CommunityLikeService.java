package com.swcamp9th.bangflixbackend.domain.community.communityPost.service;

import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityLikeDTO;

public interface CommunityLikeService {

    void addLike(CommunityLikeDTO newLike);
}
