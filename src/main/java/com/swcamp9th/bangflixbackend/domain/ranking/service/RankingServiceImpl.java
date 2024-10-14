package com.swcamp9th.bangflixbackend.domain.ranking.service;

import com.swcamp9th.bangflixbackend.domain.ranking.dto.ReviewLikeCountDTO;
import com.swcamp9th.bangflixbackend.domain.ranking.dto.ReviewRankingDTO;
import com.swcamp9th.bangflixbackend.domain.ranking.dto.ReviewRankingDateDTO;
import com.swcamp9th.bangflixbackend.domain.ranking.entity.ReviewRanking;
import com.swcamp9th.bangflixbackend.domain.ranking.repository.ReviewRankingRepository;
import com.swcamp9th.bangflixbackend.domain.review.dto.ReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.entity.Review;
import com.swcamp9th.bangflixbackend.domain.review.repository.ReviewLikeRepository;
import com.swcamp9th.bangflixbackend.domain.review.repository.ReviewRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RankingServiceImpl implements RankingService {

    private ReviewRepository reviewRepository;
    private ReviewLikeRepository reviewLikeRepository;
    private ReviewRankingRepository reviewRankingRepository;
    private ModelMapper modelMapper;

    @Autowired
    public RankingServiceImpl(ReviewRepository reviewRepository
                            , ReviewLikeRepository reviewLikeRepository
                            , ReviewRankingRepository reviewRankingRepository
                            , ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.reviewLikeRepository = reviewLikeRepository;
        this.reviewRankingRepository = reviewRankingRepository;
        this.modelMapper = modelMapper;
    }

    @Scheduled(cron = "0 0 1 * * SUN")
    @Override
    public void createReviewRanking() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekAgo = now.minusWeeks(1);  // 현재로부터 1주일 이전

        List<ReviewLikeCountDTO> reviewLikes = reviewLikeRepository.findTop5ReviewCodes(oneWeekAgo);
        List<ReviewLikeCountDTO> top5ReviewLikes = reviewLikes.size() > 5 ? reviewLikes.subList(0, 5) : reviewLikes;

        for(ReviewLikeCountDTO reviewLike : top5ReviewLikes) {
            Review review = reviewRepository.findById(reviewLike.getReviewCode()).orElseThrow();
            reviewRankingRepository.save(ReviewRanking.builder()
                .createdAt(LocalDateTime.now())
                .active(true)
                .review(review)
                .build());
        }
        
        // 매주 일요일 새벽 1시에 실행될 작업
        log.info("일요일 새벽 1시에 랭킹 생성");
    }

    @Override
    public ReviewRankingDateDTO findReviewRankingDate(Integer year) {
        return ReviewRankingDateDTO.builder().ReviewRankingDates(reviewRankingRepository.findDistinctDatesByYear(year)).build();
    }

    @Override
    public List<ReviewDTO> findReviewRanking(String date) {

        List<ReviewRankingDTO> reviews = reviewRankingRepository.findByCreatedAtDate(date);
        log.info("리뷰가 조회되는지 확인" + reviews.get(0).getReviewCode());
        return List.of();
    }
}
