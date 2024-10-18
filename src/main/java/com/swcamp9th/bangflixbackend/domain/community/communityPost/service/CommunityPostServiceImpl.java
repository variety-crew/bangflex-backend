package com.swcamp9th.bangflixbackend.domain.community.communityPost.service;

import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityPostCreateDTO;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityPostDTO;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityPostUpdateDTO;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.CommunityFile;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.repository.CommunityFileRepository;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.repository.CommunityPostRepository;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.CommunityPost;
import com.swcamp9th.bangflixbackend.domain.user.entity.Member;
import com.swcamp9th.bangflixbackend.domain.user.repository.UserRepository;
import com.swcamp9th.bangflixbackend.exception.InvalidUserException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("communityPostService")
public class CommunityPostServiceImpl implements CommunityPostService {

    private final ModelMapper modelMapper;
    private final CommunityPostRepository communityPostRepository;
    private final UserRepository userRepository;
    private final CommunityFileRepository communityFileRepository;

    @Autowired
    public CommunityPostServiceImpl(ModelMapper modelMapper,
                                    CommunityPostRepository communityPostRepository,
                                    UserRepository userRepository,
                                    CommunityFileRepository communityFileRepository) {
        this.modelMapper = modelMapper;
        this.communityPostRepository = communityPostRepository;
        this.userRepository = userRepository;
        this.communityFileRepository = communityFileRepository;
    }

    @Transactional
    @Override
    public void createPost(String loginId, CommunityPostCreateDTO newPost, List<MultipartFile> images) throws IOException {
        CommunityPost createdPost = modelMapper.map(newPost, CommunityPost.class);

        // 회원이 아니라면 예외 발생
        Member member = userRepository.findById(loginId).orElseThrow(
                () -> new InvalidUserException("회원가입이 필요합니다."));

        createdPost.setTitle(newPost.getTitle());
        createdPost.setContent(newPost.getContent());
        createdPost.setCreatedAt(LocalDateTime.now());
        createdPost.setActive(true);
        createdPost.setMember(member);

        // 게시글 저장
        communityPostRepository.save(createdPost);

        // 게시글 첨부파일 있으면 저장
        if (images != null) {
            List<CommunityFile> addedImages = saveFiles(images, createdPost);
            createdPost.setCommunityFiles(addedImages);
        }
    }

    private List<CommunityFile> saveFiles(List<MultipartFile> images, CommunityPost savedPost) throws IOException {
        List<CommunityFile> communityFiles = new ArrayList<>();

        for (MultipartFile file : images) {
            String fileName = file.getOriginalFilename();

            // 파일이름만 남김
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
            // UUID 생성
            String uuid = UUID.randomUUID().toString();
            // 저장 경로
            String filePath = "src/main/resources/static/communityFiles/" + uuid + fileName;
            Path path = Paths.get(filePath);
            // DB 저장명
            String dbUrl = "/communityFiles/" + uuid + fileName;

            //저장
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            CommunityFile addedImages = communityFileRepository.save(CommunityFile.builder()
                    .url(dbUrl)
                    .createdAt(LocalDateTime.now())
                    .active(true)
                    .communityPost(savedPost)
                    .build()
            );

            communityFiles.add(addedImages);
        }

        return communityFiles;
    }

    @Transactional
    @Override
    public void updatePost(String loginId, int communityPostCode, CommunityPostUpdateDTO modifiedPost, List<MultipartFile> images) {
        CommunityPost foundPost = communityPostRepository.findById(communityPostCode)
                                    .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        // 회원이 아니라면 예외 발생
        Member author = userRepository.findById(loginId).orElseThrow(
                () -> new InvalidUserException("로그인이 필요합니다."));

        // 게시글 작성자가 아니라면 예외 발생
        if (!foundPost.getMember().getMemberCode().equals(author.getMemberCode())) {
            throw new InvalidUserException("게시글 수정 권한이 없습니다.");
        }

        foundPost.setTitle(modifiedPost.getTitle());
        foundPost.setContent(modifiedPost.getContent());

        // 수정된 게시글 저장
        communityPostRepository.save(foundPost);
    }

    @Transactional
    @Override
    public void deletePost(String loginId, int communityPostCode) {
        CommunityPost foundPost = communityPostRepository.findById(communityPostCode)
                                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        // 회원이 아니라면 예외 발생
        Member author = userRepository.findById(loginId).orElseThrow(
                () -> new InvalidUserException("로그인이 필요합니다."));

        // 게시글 작성자가 아니라면 예외 발생
        if (!foundPost.getMember().getMemberCode().equals(author.getMemberCode())) {
            throw new InvalidUserException("게시글 삭제 권한이 없습니다.");
        }

        foundPost.setActive(false);

        communityPostRepository.save(foundPost);
    }

//    @Transactional(readOnly = true)
//    @Override
//    public Page<CommunityPostDTO> findPostList(Pageable pageable) {
//        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
//                pageable.getPageSize(),
//                Sort.by("communityPostCode").descending());
//
//        Page<CommunityPost> postList = communityPostRepository.findByActiveTrue(pageable);
//
//        List<CommunityPostDTO> posts = postList.getContent().stream()
//                .map(post -> {
//                    CommunityPostDTO dto = modelMapper.map(post, CommunityPostDTO.class);
//                    dto.setMemberCode(post.getMember().getMemberCode());
//                    return dto;
//                })
//                .toList();
//
//        return new PageImpl<>(posts, pageable, postList.getTotalElements());
//    }

    @Transactional(readOnly = true)
    @Override
    public List<CommunityPostDTO> getAllPosts() {
        List<CommunityPost> allPosts = communityPostRepository.findByActiveTrue(
                                                                Sort.by("createdAt").descending());

        List<CommunityPostDTO> postList = allPosts.stream()
                .map(communityPost -> {
                    CommunityPostDTO postDTO = modelMapper.map(communityPost, CommunityPostDTO.class);

                    List<CommunityFile> images = communityFileRepository.findByCommunityPost(communityPost);
                    List<String> urls = images.stream().map(CommunityFile::getUrl).toList();

                    postDTO.setNickname(communityPost.getMember().getNickname());
                    postDTO.setImageUrls(urls);
                    return postDTO;
                }).toList();

        return postList;
    }

    @Transactional(readOnly = true)
    @Override
    public CommunityPostDTO findPostByCode(int communityPostCode) {
        CommunityPost post = communityPostRepository.findById(communityPostCode)
                            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));

        CommunityPostDTO selectedPost = modelMapper.map(post, CommunityPostDTO.class);
        selectedPost.setNickname(post.getMember().getNickname());

        List<CommunityFile> images = communityFileRepository.findByCommunityPost(post);
        List<String> urls = images.stream().map(CommunityFile::getUrl).toList();
        selectedPost.setImageUrls(urls);

        return selectedPost;
    }
}
