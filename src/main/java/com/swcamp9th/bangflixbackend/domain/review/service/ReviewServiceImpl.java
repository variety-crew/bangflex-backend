package com.swcamp9th.bangflixbackend.domain.review.service;

import com.swcamp9th.bangflixbackend.domain.review.dto.CreateReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.ReviewCodeDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.ReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.StatisticsReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.UpdateReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.entity.Review;
import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewFile;
import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewLike;
import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewMember;
import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewTheme;
import com.swcamp9th.bangflixbackend.domain.review.repository.ReviewFileRepository;
import com.swcamp9th.bangflixbackend.domain.review.repository.ReviewLikeRepository;
import com.swcamp9th.bangflixbackend.domain.review.repository.ReviewMemberRepository;
import com.swcamp9th.bangflixbackend.domain.review.repository.ReviewRepository;
import com.swcamp9th.bangflixbackend.domain.review.repository.ReviewTendencyGenreRepository;
import com.swcamp9th.bangflixbackend.domain.review.repository.ReviewThemeRepository;
import com.swcamp9th.bangflixbackend.exception.AlreadyLikedException;
import com.swcamp9th.bangflixbackend.exception.InvalidUserException;
import com.swcamp9th.bangflixbackend.exception.LikeNotFoundException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Pageable;
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
    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewTendencyGenreRepository reviewTendencyGenreRepository;
    private ResourceLoader resourceLoader;

    @Autowired
    public ReviewServiceImpl(ModelMapper modelMapper
                           , ReviewRepository reviewRepository
                           , ReviewFileRepository reviewFileRepository
                           , ReviewThemeRepository reviewThemeRepository
                           , ReviewMemberRepository reviewMemberRepository
                           , ReviewLikeRepository reviewLikeRepository
                           , ReviewTendencyGenreRepository reviewTendencyGenreRepository
                           , ResourceLoader resourceLoader) {
        this.modelMapper = modelMapper;
        this.reviewRepository = reviewRepository;
        this.reviewFileRepository = reviewFileRepository;
        this.reviewThemeRepository = reviewThemeRepository;
        this.reviewMemberRepository = reviewMemberRepository;
        this.reviewLikeRepository = reviewLikeRepository;
        this.reviewTendencyGenreRepository = reviewTendencyGenreRepository;
        this.resourceLoader = resourceLoader;
    }

    @Override
    @Transactional
    public void createReview(CreateReviewDTO newReview, List<MultipartFile> images)
        throws IOException, URISyntaxException {

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
        if(images != null)
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
    @Transactional
    public void deleteReview(ReviewCodeDTO reviewCodeDTO) {

        // 기존 리뷰 조회
        Review existingReview = reviewRepository.findById(reviewCodeDTO.getReviewCode()).orElse(null);

        if(reviewCodeDTO.getMemberCode().equals(existingReview.getMember().getMemberCode())) {
            existingReview.setActive(false);
            reviewRepository.save(existingReview);
        }
        else
            throw new InvalidUserException("리뷰 삭제 권한이 없습니다");
    }

    @Override
    @Transactional
    public List<ReviewDTO> findReviewsWithFilters(Integer themeCode, String filter, Pageable pageable) {

        // 테마 코드로 리뷰를 모두 조회
        List<Review> reviews = reviewRepository.findByThemeCodeAndActiveTrueWithFetchJoin(themeCode, pageable);

        // 필터가 있을 경우 해당 조건에 맞게 정렬
        if (filter != null) {
            switch (filter) {
                case "highScore":

                    // 점수 높은 순 정렬
                    reviews.sort(Comparator.comparing(Review::getTotalScore).reversed()
                        .thenComparing(Comparator.comparing(Review::getCreatedAt).reversed()));
                    break;
                case "lowScore":

                    // 점수 낮은 순 정렬
                    reviews.sort(Comparator.comparing(Review::getTotalScore)
                        .thenComparing(Comparator.comparing(Review::getCreatedAt).reversed()));
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

        return getReviewDTOS(reviews);
    }

    @Override
    @Transactional
    public List<ReviewDTO> getReviewDTOS(List<Review> sublist) {
        List<ReviewDTO> result = sublist.stream()
            .map(review -> {
                ReviewDTO reviewDTO = modelMapper.map(review, ReviewDTO.class);

                // 이미지 경로 추가
                reviewDTO.setImagePaths(findImagePathsByReviewCode(review.getReviewCode()));
                reviewDTO.setLikes(findReviewLikesByReviewCode(review.getReviewCode()));
                reviewDTO.setMemberNickname(review.getMember().getNickname());
                reviewDTO.setReviewCode(review.getReviewCode());
                reviewDTO.setMemberCode(review.getMember().getMemberCode());
                reviewDTO.setMemberImage(review.getMember().getImage());
                List<String> genres = findMemberTendencyGenre(review.getMember().getMemberCode());

                if(!genres.isEmpty())
                    reviewDTO.setGenres(genres);

                return reviewDTO;
            }).toList();

        return result;
    }

    @Override
    @Transactional
    public ReviewDTO getReviewDTO(Review review) {

        ReviewDTO reviewDTO = modelMapper.map(review, ReviewDTO.class);

        // 이미지 경로 추가
        reviewDTO.setImagePaths(findImagePathsByReviewCode(review.getReviewCode()));
        reviewDTO.setLikes(findReviewLikesByReviewCode(review.getReviewCode()));
        reviewDTO.setMemberNickname(review.getMember().getNickname());
        reviewDTO.setReviewCode(review.getReviewCode());
        reviewDTO.setMemberCode(review.getMember().getMemberCode());
        reviewDTO.setMemberImage(review.getMember().getImage());
        List<String> genres = findMemberTendencyGenre(review.getMember().getMemberCode());

        if(!genres.isEmpty())
            reviewDTO.setGenres(genres);

        return reviewDTO;
    }

    @Override
    @Transactional
    public StatisticsReviewDTO findReviewStatistics(Integer themeCode) {
        return reviewRepository.findStatisticsByThemeCode(themeCode);
    }

    @Override
    @Transactional
    public void likeReview(ReviewCodeDTO reviewCodeDTO) {

        ReviewLike reviewLike = reviewLikeRepository.findByMemberCodeAndReviewCode(
            reviewCodeDTO.getMemberCode(), reviewCodeDTO.getReviewCode());

        if (reviewLike == null) {

            ReviewLike newReviewLike = new ReviewLike();
            newReviewLike.setMemberCode(reviewCodeDTO.getMemberCode());
            newReviewLike.setReviewCode(reviewCodeDTO.getReviewCode());
            newReviewLike.setCreatedAt(LocalDateTime.now());
            newReviewLike.setActive(true);

            reviewLikeRepository.save(newReviewLike);
        } else {
            if(reviewLike.getActive()) {
                throw new AlreadyLikedException("이미 좋아요가 존재합니다.");
            } else{
                reviewLike.setActive(true);
                reviewLikeRepository.save(reviewLike);
            }
        }
    }

    @Override
    @Transactional
    public void deleteLikeReview(ReviewCodeDTO reviewCodeDTO) {
        ReviewLike reviewLike = reviewLikeRepository.findByMemberCodeAndReviewCode(
            reviewCodeDTO.getMemberCode(), reviewCodeDTO.getReviewCode());

        if (reviewLike == null) {
            throw new LikeNotFoundException("좋아요가 존재하지 않습니다.");
        } else {
            if(reviewLike.getActive()) {
                reviewLike.setActive(false);
                reviewLikeRepository.save(reviewLike);
            } else{
                throw new LikeNotFoundException("좋아요가 존재하지 않습니다.");
            }
        }
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
            String filePath = uploadsDir + "/" + fileName;
            // DB에 저장할 경로 문자열
            String dbFilePath = "/uploadFiles/reviewFile/" + fileName;

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

    public List<String> findImagePathsByReviewCode(Integer reviewCode) {
        return reviewFileRepository.findByReview_ReviewCode(reviewCode)
            .stream().map(ReviewFile::getUrl).toList();
    }

    private Integer findReviewLikesByReviewCode(Integer reviewCode) {
        return reviewLikeRepository.findByReviewCode(reviewCode).size();
    }

    private List<String> findMemberTendencyGenre(Integer memberCode) {
        return reviewTendencyGenreRepository
            .findMemberGenreByMemberCode(memberCode).stream()
            .map(reviewTendencyGenre -> reviewTendencyGenre.getGenre().getName()).toList();
    }
}
