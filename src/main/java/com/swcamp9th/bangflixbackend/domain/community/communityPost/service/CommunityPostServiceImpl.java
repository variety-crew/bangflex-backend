package com.swcamp9th.bangflixbackend.domain.community.communityPost.service;

import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityPostDeleteDTO;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityPostRequestDTO;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityPostResponseDTO;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityPostUpdateDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("communityPostService")
public class CommunityPostServiceImpl implements CommunityPostService {

    private final ModelMapper modelMapper;
    private final CommunityPostRepository communityPostRepository;
    private final MemberRepository memberRepository;
    private final CommunityFileRepository communityFileRepository;

    @Autowired
    public CommunityPostServiceImpl(ModelMapper modelMapper,
                                    CommunityPostRepository communityPostRepository,
                                    MemberRepository memberRepository,
                                    CommunityFileRepository communityFileRepository) {
        this.modelMapper = modelMapper;
        this.communityPostRepository = communityPostRepository;
        this.memberRepository = memberRepository;
        this.communityFileRepository = communityFileRepository;
    }

    @Transactional
    @Override
    public CommunityPostResponseDTO createPost(CommunityPostRequestDTO newPost, List<MultipartFile> images) throws IOException {
        CommunityPost createdPost = modelMapper.map(newPost, CommunityPost.class);

        // 회원이 아니라면 예외 발생
        Member author = memberRepository.findById(newPost.getMemberCode()).orElseThrow(
                () -> new InvalidUserException("게시글 작성 권한이 없습니다.")
        );

        createdPost.setTitle(newPost.getTitle());
        createdPost.setContent(newPost.getContent());
        createdPost.setCreatedAt(LocalDateTime.now());
        createdPost.setActive(true);
        createdPost.setMember(author);

        // 게시글 저장
        CommunityPost savedPost = communityPostRepository.save(createdPost);

        CommunityPostResponseDTO postResponse = modelMapper.map(savedPost, CommunityPostResponseDTO.class);
        postResponse.setMemberCode(savedPost.getMember().getMemberCode());

        // 게시글 첨부파일 있으면 저장
        if (newPost.getImages() != null) {
            List<String> imageUrls = saveFiles(images, savedPost);
            postResponse.setImageUrls(imageUrls);
        }

        return postResponse;
    }

    private List<String> saveFiles(List<MultipartFile> images, CommunityPost savedPost) throws IOException {
        List<String> urls = new ArrayList<>();

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
                    .communityPost(savedPost)
                    .build()
            );

            urls.add(dbUrl);
        }
        return urls;
    }

    @Transactional
    @Override
    public void updatePost(Integer communityPostCode, CommunityPostUpdateDTO modifiedPost, List<MultipartFile> images) {
        CommunityPost foundPost = communityPostRepository.findById(communityPostCode)
                                    .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        // 현재 사용자가 게시글 작성자인지 체크 후 아니라면 예외 발생
        if (!foundPost.getMember().getMemberCode().equals(modifiedPost.getMemberCode())) {
            throw new InvalidUserException("게시글 수정 권한이 없습니다.");
        }

        foundPost.setTitle(modifiedPost.getTitle());
        foundPost.setContent(modifiedPost.getContent());

        // 수정된 게시글 저장
        communityPostRepository.save(foundPost);
    }

    @Transactional
    @Override
    public void deletePost(Integer communityPostCode, CommunityPostDeleteDTO deletedPost) {
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
