package com.swcamp9th.bangflixbackend.domain.community.communityPost.service;

import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityPostRequestDTO;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityPostResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CommunityPostService {

    void createPost(CommunityPostRequestDTO newPost) throws IOException;

    void modifyPost(Integer communityPostCode, CommunityPostResponseDTO modifiedPost, List<MultipartFile> images) throws IOException;

    void deletePost(Integer communityPostCode, CommunityPostResponseDTO deletedPost);

    Page<CommunityPostResponseDTO> findPostList(Pageable pageable);

    CommunityPostResponseDTO findPostByCode(Integer communityPostCode);
}
