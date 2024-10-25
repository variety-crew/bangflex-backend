package com.swcamp9th.bangflixbackend.domain.communityPost.command.service;

import com.swcamp9th.bangflixbackend.domain.communityPost.command.dto.CommunityLikeCountDTO;
import com.swcamp9th.bangflixbackend.domain.communityPost.command.dto.CommunityLikeCreateDTO;

public interface CommunityLikeService {

    void addLike(String loginId, CommunityLikeCreateDTO newLike);

    CommunityLikeCountDTO countLike(int communityPostCode);
}
