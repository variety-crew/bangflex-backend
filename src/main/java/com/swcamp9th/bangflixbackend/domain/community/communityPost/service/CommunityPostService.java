package com.swcamp9th.bangflixbackend.domain.community.communityPost.service;

import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityPostDeleteDTO;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityPostRequestDTO;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityPostResponseDTO;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityPostUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CommunityPostService {

    CommunityPostResponseDTO createPost(CommunityPostRequestDTO newPost, List<MultipartFile> images) throws IOException;

    void updatePost(Integer communityPostCode, CommunityPostUpdateDTO modifiedPost, List<MultipartFile> images);

    void deletePost(Integer communityPostCode, CommunityPostDeleteDTO deletedPost);

    Page<CommunityPostResponseDTO> findPostList(Pageable pageable);

    CommunityPostResponseDTO findPostByCode(Integer communityPostCode);
}
