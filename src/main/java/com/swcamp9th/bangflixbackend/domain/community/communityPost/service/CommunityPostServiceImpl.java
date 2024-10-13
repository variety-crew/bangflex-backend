package com.swcamp9th.bangflixbackend.domain.community.communityPost.service;

import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityPostRequestDTO;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityPostResponseDTO;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.CommunityFile;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.Member;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.repository.CommunityFileRepository;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.repository.CommunityPostRepository;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.CommunityPost;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.repository.MemberRepository;
import com.swcamp9th.bangflixbackend.exception.InvalidUserException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service("communityPostService")
public class CommunityPostServiceImpl implements CommunityPostService {

    private final ModelMapper modelMapper;
    private final CommunityPostRepository communityPostRepository;
    private final MemberRepository memberRepository;
    private final CommunityFileService communityFileService;

    @Autowired
    public CommunityPostServiceImpl(ModelMapper modelMapper,
                                    CommunityPostRepository communityPostRepository,
                                    MemberRepository memberRepository,
                                    CommunityFileService communityFileService) {
        this.modelMapper = modelMapper;
        this.communityPostRepository = communityPostRepository;
        this.memberRepository = memberRepository;
        this.communityFileService = communityFileService;
    }

    @Transactional
    @Override
    public void createPost(CommunityPostRequestDTO newPost) throws IOException {
        CommunityPost post = modelMapper.map(newPost, CommunityPost.class);

        // 회원이 아니라면 예외 발생
        Member author = memberRepository.findById(newPost.getMemberCode()).orElseThrow(
                () -> new InvalidUserException("게시글 작성 권한이 없습니다.")
        );

        post.setTitle(newPost.getTitle());
        post.setContent(newPost.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setActive(true);
        post.setMember(author);

        // 게시글 저장
        CommunityPost savedPost = communityPostRepository.save(post);

        // 게시글 첨부파일 있으면 업로드 및 url 저장
        if (newPost.getImages() != null) {
            List<String> imageUrls = communityFileService.uploadFiles(savedPost.getCommunityPostCode(),
                                                                        newPost.getImages());

            CommunityPostResponseDTO postResponse = modelMapper.map(savedPost, CommunityPostResponseDTO.class);
            postResponse.setImageUrls(imageUrls);
            communityPostRepository.save(modelMapper.map(postResponse, CommunityPost.class));
        }
    }

    @Transactional
    @Override
    public void modifyPost(Integer communityPostCode,
                           CommunityPostResponseDTO modifiedPost,
                           List<MultipartFile> images) throws IOException {
        CommunityPost foundPost = communityPostRepository.findById(communityPostCode)
                                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        // 현재 사용자가 게시글 작성자인지 체크 후 아니라면 예외 발생
        if (!foundPost.getMember().getMemberCode().equals(modifiedPost.getMemberCode())) {
            throw new InvalidUserException("게시글 수정 권한이 없습니다.");
        }

//        foundPost.setTitle(modifiedPost.getTitle());
//        foundPost.setContent(modifiedPost.getContent());
////        foundPost.setMember(memberRepository.findById(modifiedPost.getMemberCode())
////                                            .orElseThrow(IllegalArgumentException::new));
//        if(images != null) saveCommunityFiles(images, foundPost);
    }

    @Transactional
    @Override
    public void deletePost(Integer communityPostCode, CommunityPostResponseDTO deletedPost) {
        CommunityPost foundPost = communityPostRepository.findById(communityPostCode)
                                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        // 현재 사용자가 게시글 작성자인지 체크 후 아니라면 예외 발생
        if (!foundPost.getMember().getMemberCode().equals(deletedPost.getMemberCode())) {
            throw new InvalidUserException("게시글 삭제 권한이 없습니다.");
        }

        foundPost.setActive(false);
        communityPostRepository.save(foundPost);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<CommunityPostResponseDTO> findPostList(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("communityPostCode").descending());

        Page<CommunityPost> postList = communityPostRepository.findAll(pageable);

        return postList.map(post -> modelMapper.map(post, CommunityPostResponseDTO.class));
    }

    @Transactional(readOnly = true)
    @Override
    public CommunityPostResponseDTO findPostByCode(Integer communityPostCode) {
        CommunityPost post = communityPostRepository.findById(communityPostCode)
                            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        return modelMapper.map(post, CommunityPostResponseDTO.class);
    }
}
