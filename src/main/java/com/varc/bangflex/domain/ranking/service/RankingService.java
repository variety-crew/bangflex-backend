package com.varc.bangflex.domain.ranking.service;

import com.varc.bangflex.domain.ranking.dto.MemberRankingDTO;
import com.varc.bangflex.domain.ranking.dto.ReviewRankingDTO;
import com.varc.bangflex.domain.ranking.dto.ReviewRankingDateDTO;
import com.varc.bangflex.domain.review.dto.ReviewDTO;
import java.util.List;

import org.springframework.data.domain.Pageable;


public interface RankingService {
    void createReviewRanking();

    ReviewRankingDateDTO findReviewRankingDate(Integer year);

    List<ReviewRankingDTO> findReviewRanking(String date, String loginId);

    List<ReviewDTO> findAllReviewRanking(Pageable pageable, String loginId);

    List<MemberRankingDTO> findAllMemberRanking(Pageable pageable);
}
