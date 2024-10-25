package com.varc.bangflex.domain.communityPost.command.service;

import com.varc.bangflex.domain.communityPost.command.dto.CommunityLikeCountDTO;
import com.varc.bangflex.domain.communityPost.command.dto.CommunityLikeCreateDTO;

public interface CommunityLikeService {

    void addLike(String loginId, CommunityLikeCreateDTO newLike);

    CommunityLikeCountDTO countLike(int communityPostCode);
}
