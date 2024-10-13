package com.swcamp9th.bangflixbackend.domain.community.communityPost.service;

import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityPostDTO;
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

@Service
public class CommunityPostServiceImpl implements CommunityPostService {

    private final ModelMapper modelMapper;
    private final CommunityPostRepository communityPostRepository;
    private final CommunityFileRepository communityFileRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public CommunityPostServiceImpl(ModelMapper modelMapper,
                                    CommunityPostRepository communityPostRepository,
                                    CommunityFileRepository communityFileRepository,
                                    MemberRepository memberRepository) {
        this.modelMapper = modelMapper;
        this.communityPostRepository = communityPostRepository;
        this.communityFileRepository = communityFileRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    @Override
    public void createPost(CommunityPostDTO newPost, List<MultipartFile> images) throws IOException {
        CommunityPost postEntity = modelMapper.map(newPost, CommunityPost.class);

        // 회원이 아니라면 예외 발생
        Member author = memberRepository.findById(newPost.getMemberCode()).orElseThrow(
                () -> new InvalidUserException("게시글 작성 권한이 없습니다.")
        );

        postEntity.setMember(author);
        postEntity.setCreatedAt(LocalDateTime.now());
        postEntity.setActive(true);
        CommunityPost createdPost = communityPostRepository.save(postEntity);

        // 게시글 파일 저장
        saveCommunityFiles(images, createdPost);
    }

    private void saveCommunityFiles(List<MultipartFile> images, CommunityPost createdPost) throws IOException {
        for (MultipartFile file : images) {
            String fileName = file.getOriginalFilename();

            // 파일이름만 남김
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
            // UUID 생성
            String uuid = UUID.randomUUID().toString();
            // 저장 경로
            String filePath = "src/main/resources/static/communityFiles" + uuid + fileName;
            Path path = Paths.get(filePath);
            // DB 저장명
            String dbUrl = "/communityFiles" + uuid + fileName;

            //저장
            Files.copy(file.getInputStream(),
                    path,
                    StandardCopyOption.REPLACE_EXISTING     // 이미 파일이 존재하면 덮어쓰기
            );

            communityFileRepository.save(CommunityFile.builder()
                    .url(dbUrl)
                    .createdAt(LocalDateTime.now())
                    .active(true)
                    .communityPost(createdPost)
                    .build()
            );
        }

        communityPostRepository.save(createdPost);
    }

    @Transactional
    @Override
    public void modifyPost(Integer communityPostCode,
                           CommunityPostDTO modifiedPost,
                           List<MultipartFile> images) throws IOException {
        CommunityPost foundPost = communityPostRepository.findById(communityPostCode)
                                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        // 현재 사용자가 게시글 작성자인지 체크 후 아니라면 예외 발생
        if (!foundPost.getMember().getMemberCode().equals(modifiedPost.getMemberCode())) {
            throw new InvalidUserException("게시글 수정 권한이 없습니다.");
        }

        foundPost.setTitle(modifiedPost.getTitle());
        foundPost.setContent(modifiedPost.getContent());
        saveCommunityFiles(images, foundPost);
    }

    @Transactional
    @Override
    public void deletePost(Integer communityPostCode, CommunityPostDTO deletedPost) {
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
