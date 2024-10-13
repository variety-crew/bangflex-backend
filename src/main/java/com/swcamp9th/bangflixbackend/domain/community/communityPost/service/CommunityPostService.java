package com.swcamp9th.bangflixbackend.domain.community.communityPost.service;

import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityPostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CommunityPostService {

    void createPost(CommunityPostDTO newPost, List<MultipartFile> images) throws IOException;

    void modifyPost(Integer communityPostCode, CommunityPostDTO modifiedPost, List<MultipartFile> images) throws IOException;

    void deletePost(Integer communityPostCode, CommunityPostDTO deletedPost);

    Page<CommunityPostDTO> findPostList(Pageable pageable);

    CommunityPostDTO findPostByCode(Integer communityPostCode);
}
