package com.swcamp9th.bangflixbackend.domain.review.service;

import com.swcamp9th.bangflixbackend.domain.review.dto.CreateReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.DeleteReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.ReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.UpdateReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.entity.Review;
import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewFile;
import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewMember;
import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewTheme;
import com.swcamp9th.bangflixbackend.domain.review.enums.Level;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
            throw new InvalidUserException("리뷰 수정 권한이 없습니다");

        reviewRepository.save(existingReview);
    }

    @Override
    public void deleteReview(DeleteReviewDTO deleteReviewDTO) {
        // 기존 리뷰 조회
        Review existingReview = reviewRepository.findById(deleteReviewDTO.getReviewCode()).orElse(null);

        if(deleteReviewDTO.getMemberCode().equals(existingReview.getMember().getMemberCode())) {
            existingReview.setActive(false);
            reviewRepository.save(existingReview);
        }
        else
            throw new InvalidUserException("리뷰 삭제 권한이 없습니다");
    }


    public List<ReviewDTO> findReviewsWithFilters(Integer themeCode, String filter, Integer lastReviewCode) {
        // 테마 코드로 리뷰를 모두 조회
        List<Review> reviews = reviewRepository.findByThemeCodeAndActiveTrueWithFetchJoin(themeCode);

        log.info("리뷰 코드 : " + reviews.get(0).getReviewCode());

        // 필터가 있을 경우 해당 조건에 맞게 정렬
        if (filter != null) {
            switch (filter) {
                case "highScore":
                    // 점수 높은 순 정렬
                    reviews.sort(Comparator.comparing(Review::getTotalScore).reversed().thenComparing(Review::getCreatedAt));
                    break;
                case "lowScore":
                    // 점수 낮은 순 정렬
                    reviews.sort(Comparator.comparing(Review::getTotalScore).thenComparing(Review::getCreatedAt));
                    break;
                default:
                    // 필터가 일치하지 않으면 최신순으로 정렬 (기본값)
                    reviews.sort(Comparator.comparing(Review::getCreatedAt).reversed());
                    break;
            }
        } else {
            // 필터가 없으면 최신순으로 정렬 (기본값)
            reviews.sort(Comparator.comparing(Review::getCreatedAt).reversed());
        }

        // lastReviewCode 기준으로 인덱스를 찾음
        int startIndex = 0;
        if (lastReviewCode != null) {
            startIndex = findReviewIndex(reviews, lastReviewCode);
        }

        log.info("리뷰 코드 : " + reviews.get(0).getMember());

        // 인덱스가 유효하면 그 이후의 10개 리뷰 반환
        if (startIndex >= 0 && startIndex < reviews.size()) {
            List<Review> sublist = reviews.subList(startIndex, Math.min(startIndex + 10, reviews.size()));

            return sublist.stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());
        }

        // 유효하지 않으면 빈 리스트 반환
        return Collections.emptyList();
    }

    // 리뷰 리스트에서 lastReviewCode에 해당하는 리뷰의 인덱스를 찾는 메서드
    private int findReviewIndex(List<Review> reviews, Integer lastReviewCode) {
        for (int i = 0; i < reviews.size(); i++) {
            if (reviews.get(i).getReviewCode().equals(lastReviewCode)) {
                return i + 1; // 찾은 인덱스 바로 다음부터 시작
            }
        }
        return 0; // 못 찾으면 처음부터
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
