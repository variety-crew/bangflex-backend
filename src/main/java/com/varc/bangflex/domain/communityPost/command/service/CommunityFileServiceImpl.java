//package com.varc.bangflex.domain.community.communityPost.service;
//
//import com.varc.bangflex.domain.community.communityPost.entity.CommunityFile;
//import com.varc.bangflex.domain.community.communityPost.entity.CommunityPost;
//import com.varc.bangflex.domain.community.communityPost.repository.CommunityFileRepository;
//import com.varc.bangflex.domain.community.communityPost.repository.CommunityPostRepository;
//import jakarta.persistence.EntityNotFoundException;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Service("communityFileService")
//public class CommunityFileServiceImpl implements CommunityFileService {
//
//    private final ModelMapper modelMapper;
//    private final CommunityPostRepository communityPostRepository;
//    private final CommunityFileRepository communityFileRepository;
//
//    @Autowired
//    public CommunityFileServiceImpl(ModelMapper modelMapper,
//                                    CommunityPostRepository communityPostRepository,
//                                    CommunityFileRepository communityFileRepository) {
//        this.modelMapper = modelMapper;
//        this.communityPostRepository = communityPostRepository;
//        this.communityFileRepository = communityFileRepository;
//    }
//
//    @Transactional
//    @Override
//    public List<String> uploadFiles(Integer communityPostCode, List<MultipartFile> images) throws IOException {
//        List<String> urls = new ArrayList<>();
//        CommunityPost post = communityPostRepository.findById(communityPostCode)
//                                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
//
//        for (MultipartFile file : images) {
//
//            // 첨부파일 url 저장
//            String imageUrl = saveFile(file);
//            communityFileRepository.save(CommunityFile.builder()
//                    .url(imageUrl)
//                    .createdAt(LocalDateTime.now())
//                    .active(true)
//                    .communityPost(post)
//                    .build()
//            );
//            urls.add(imageUrl);
//        }
//
//        return urls;
//    }
//
//    private String saveFile(MultipartFile file) throws IOException {
//        String fileName = file.getOriginalFilename();
//
//        // 파일이름만 남김
//        fileName = fileName.substring(0, fileName.lastIndexOf("."));
//        // UUID 생성
//        String uuid = UUID.randomUUID().toString();
//        // 저장 경로
//        String filePath = "src/main/resources/static/communityFiles" + uuid + fileName;
//        Path path = Paths.get(filePath);
//        // DB 저장명
//        String dbUrl = "/communityFiles" + uuid + fileName;
//
//        //저장
//        Files.copy(file.getInputStream(),
//                path,
//                StandardCopyOption.REPLACE_EXISTING     // 이미 파일이 존재하면 덮어쓰기
//        );
//
//        return dbUrl;
//    }
//
//    @Override
//    public void updateFiles(Integer communityPostCode, List<String> newImageUrls) {
//        CommunityPost post = communityPostRepository.findById(communityPostCode)
//                                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다."));
//
//        // 게시글의 첨부파일 목록 불러오기
//        List<CommunityFile> originalFiles = communityFileRepository.findByCommunityPost(post);
//
//        // 새로운 첨부파일 url 리스트에 기존 첨부파일의 url이 없다면 기존 첨부파일은 비활성화 처리
//        for (CommunityFile file : originalFiles) {
//            if (!newImageUrls.contains(file.getUrl())) {
//                file.setActive(false);
//            }
//        }
//
//        for (String imageUrl : newImageUrls) {
//
//        }
//    }
//}
