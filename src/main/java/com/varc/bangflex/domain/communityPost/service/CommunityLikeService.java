package com.varc.bangflex.domain.communityPost.service;

import com.varc.bangflex.domain.communityPost.dto.CommunityLikeCountDTO;
import com.varc.bangflex.domain.communityPost.dto.CommunityLikeCreateDTO;

public interface CommunityLikeService {

    void addLike(String loginId, CommunityLikeCreateDTO newLike);

    CommunityLikeCountDTO countLike(int communityPostCode);
}
