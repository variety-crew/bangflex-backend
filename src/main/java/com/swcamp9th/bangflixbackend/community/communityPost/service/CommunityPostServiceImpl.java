package com.swcamp9th.bangflixbackend.community.communityPost.service;

import com.swcamp9th.bangflixbackend.community.communityPost.dto.CommunityPostDTO;
import com.swcamp9th.bangflixbackend.community.communityPost.repository.CommunityPostRepository;
import com.swcamp9th.bangflixbackend.community.entity.CommunityPost;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CommunityPostServiceImpl implements CommunityPostService {

    private final ModelMapper modelMapper;
    private final CommunityPostRepository communityPostRepository;

    @Autowired
    public CommunityPostServiceImpl(ModelMapper modelMapper, CommunityPostRepository communityPostRepository) {
        this.modelMapper = modelMapper;
        this.communityPostRepository = communityPostRepository;
    }

    @Transactional
    @Override
    public void registPost(CommunityPostDTO newPost) {
        CommunityPost postEntity = modelMapper.map(newPost, CommunityPost.class);
        // 현재 사용자를 게시글 작성자로 set
        postEntity.setCreatedAt(LocalDateTime.now());
        communityPostRepository.save(postEntity);
    }

    @Transactional
    @Override
    public void modifyPost(Integer communityPostCode, CommunityPostDTO modifiedPost) {
        CommunityPost foundPost = communityPostRepository.findById(communityPostCode)
                                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        // 현재 사용자가 게시글 작성자인지 체크 후 아니라면 예외 발생

        foundPost.setTitle(modifiedPost.getTitle());
        foundPost.setContent(modifiedPost.getContent());
    }

    @Transactional
    @Override
    public void deletePost(Integer communityPostCode, CommunityPostDTO deletedPost) {
        CommunityPost foundPost = communityPostRepository.findById(communityPostCode)
                                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        // 현재 사용자가 게시글 작성자인지 체크 후 아니라면 예외 발생

        foundPost.setActive(false);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CommunityPostDTO> findPostList(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("communityPostCode").descending());

        Page<CommunityPost> postList = communityPostRepository.findAll(pageable);

        return postList.map(post -> modelMapper.map(post, CommunityPostDTO.class));
    }

    @Transactional(readOnly = true)
    @Override
    public CommunityPostDTO findPostByCode(Integer communityPostCode) {
        CommunityPost post = communityPostRepository.findById(communityPostCode)
                            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        return modelMapper.map(post, CommunityPostDTO.class);
    }
}
