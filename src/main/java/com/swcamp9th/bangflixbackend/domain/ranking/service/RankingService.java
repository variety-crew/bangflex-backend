package com.swcamp9th.bangflixbackend.domain.ranking.service;

import com.swcamp9th.bangflixbackend.domain.ranking.dto.ReviewRankingDTO;
import com.swcamp9th.bangflixbackend.domain.ranking.dto.ReviewRankingDateDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.ReviewDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RankingService {
    void createReviewRanking();

    ReviewRankingDateDTO findReviewRankingDate(Integer year);

    List<ReviewRankingDTO> findReviewRanking(String date);

    List<ReviewDTO> findAllReviewRanking(Pageable pageable);
}
