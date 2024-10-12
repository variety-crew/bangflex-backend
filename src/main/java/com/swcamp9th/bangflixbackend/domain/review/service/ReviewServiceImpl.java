package com.swcamp9th.bangflixbackend.domain.review.service;

import com.swcamp9th.bangflixbackend.domain.review.dto.CreateReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.UpdateReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.entity.Review;
import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewFile;
import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewMember;
import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewTheme;
import com.swcamp9th.bangflixbackend.domain.review.repository.ReviewFileRepository;
import com.swcamp9th.bangflixbackend.domain.review.repository.ReviewMemberRepository;
import com.swcamp9th.bangflixbackend.domain.review.repository.ReviewRepository;
import com.swcamp9th.bangflixbackend.domain.review.repository.ReviewThemeRepository;
import com.swcamp9th.bangflixbackend.exception.InvalidUserException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ModelMapper modelMapper;
    private final ReviewRepository reviewRepository;
    private final ReviewFileRepository reviewFileRepository;
    private final ReviewThemeRepository reviewThemeRepository;
    private final ReviewMemberRepository reviewMemberRepository;

    @Autowired
    public ReviewServiceImpl(ModelMapper modelMapper
                           , ReviewRepository reviewRepository
                           , ReviewFileRepository reviewFileRepository
                           , ReviewThemeRepository reviewThemeRepository
                           , ReviewMemberRepository reviewMemberRepository) {
        this.modelMapper = modelMapper;
        this.reviewRepository = reviewRepository;
        this.reviewFileRepository = reviewFileRepository;
        this.reviewThemeRepository = reviewThemeRepository;
        this.reviewMemberRepository = reviewMemberRepository;
    }

    @Override
    @Transactional
    public void createReview(CreateReviewDTO newReview, List<MultipartFile> images) throws IOException {

        // 리뷰 저장
        Review review = modelMapper.map(newReview, Review.class);
        ReviewTheme reviewTheme = reviewThemeRepository.findById(newReview.getThemeCode()).orElse(null);
        ReviewMember reviewMember = reviewMemberRepository.findById(newReview.getMemberCode()).orElse(null);
        review.setTheme(reviewTheme);
        review.setMember(reviewMember);
        review.setActive(true);
        review.setCreatedAt(LocalDateTime.now());
        Review insertReview = reviewRepository.save(review);

        // 리뷰 파일 저장
        saveReviewFile(images, insertReview);

        // 멤버 포인트 올리기
        reviewMember.setPoint(reviewMember.getPoint()+5);
        reviewMemberRepository.save(reviewMember);

    }

    @Override
    @Transactional
    public void updateReview(UpdateReviewDTO updateReview) {


        // 기존 리뷰 조회
        Review existingReview = reviewRepository.findById(updateReview.getReviewCode()).orElse(null);

        if(updateReview.getMemberCode().equals(existingReview.getMember().getMemberCode())){
            // DTO에서 null이 아닌 값만 업데이트
            if (updateReview.getHeadcount() != null) {
                existingReview.setHeadcount(updateReview.getHeadcount());
            }
            if (updateReview.getTakenTime() != null) {
                existingReview.setTakenTime(updateReview.getTakenTime());
            }
            if (updateReview.getTotalScore() != null) {
                existingReview.setTotalScore(updateReview.getTotalScore());
            }
            if (updateReview.getComposition() != null) {
                existingReview.setComposition(updateReview.getComposition());
            }
            if (updateReview.getLevel() != null) {
                existingReview.setLevel(updateReview.getLevel());
            }
            if (updateReview.getHorrorLevel() != null) {
                existingReview.setHorrorLevel(updateReview.getHorrorLevel());
            }
            if (updateReview.getActivity() != null) {
                existingReview.setActivity(updateReview.getActivity());
            }
            if (updateReview.getInterior() != null) {
                existingReview.setInterior(updateReview.getInterior());
            }
            if (updateReview.getProbability() != null) {
                existingReview.setProbability(updateReview.getProbability());
            }
            if (updateReview.getContent() != null) {
                existingReview.setContent(updateReview.getContent());
            }
        }
        else
            throw new InvalidUserException("리뷰 작성 권한이 없습니다");

        reviewRepository.save(existingReview);
    }


    private void saveReviewFile(List<MultipartFile> images, Review review) throws IOException {
        String uploadsDir = "src/main/resources/static/uploadFiles/reviewFile";

        for(MultipartFile file : images) {
            String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + file.getOriginalFilename();
            // 실제 파일이 저장될 경로
            String filePath = uploadsDir + fileName;
            // DB에 저장할 경로 문자열
            String dbFilePath = "/uploadFiles/reviewFile" + fileName;

            Path path = Paths.get(filePath); // Path 객체 생성
            Files.createDirectories(path.getParent()); // 디렉토리 생성
            Files.write(path, file.getBytes()); // 디렉토리에 파일 저장

            reviewFileRepository.save(ReviewFile.builder()
                                .review(review)
                                .active(true)
                                .createdAt(LocalDateTime.now())
                                .url(dbFilePath)
                                .build());
        }


    }
}
