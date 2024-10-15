package com.swcamp9th.bangflixbackend.domain.ranking.service;

import com.swcamp9th.bangflixbackend.domain.ranking.dto.ReviewRankingDTO;
import com.swcamp9th.bangflixbackend.domain.ranking.dto.ReviewRankingDateDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.ReviewDTO;
import java.util.List;

public interface RankingService {
    void createReviewRanking();

    ReviewRankingDateDTO findReviewRankingDate(Integer year);

    List<ReviewRankingDTO> findReviewRanking(String date);
}
