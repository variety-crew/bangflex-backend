package com.swcamp9th.bangflixbackend.community.communityPost.service;

import com.swcamp9th.bangflixbackend.community.communityPost.dto.CommunityPostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommunityPostService {

    void registPost(CommunityPostDTO newPost);

    void modifyPost(Integer communityPostCode, CommunityPostDTO modifiedPost);

    void deletePost(Integer communityPostCode, CommunityPostDTO deletedPost);

    Page<CommunityPostDTO> findPostList(Pageable pageable);

    CommunityPostDTO findPostByCode(Integer communityPostCode);
}
