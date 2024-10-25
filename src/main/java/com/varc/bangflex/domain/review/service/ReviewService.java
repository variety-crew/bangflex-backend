package com.varc.bangflex.domain.review.service;

import com.varc.bangflex.domain.review.dto.CreateReviewDTO;
import com.varc.bangflex.domain.review.dto.ReviewCodeDTO;
import com.varc.bangflex.domain.review.dto.ReviewDTO;
import com.varc.bangflex.domain.review.dto.ReviewReportDTO;
import com.varc.bangflex.domain.review.dto.StatisticsReviewDTO;
import com.varc.bangflex.domain.review.dto.UpdateReviewDTO;
import com.varc.bangflex.domain.review.entity.Review;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ReviewService {

    void createReview(CreateReviewDTO newReview, List<MultipartFile> images, String loginId)
        throws IOException, URISyntaxException;

    void updateReview(UpdateReviewDTO updateReview, String loginId);

    void deleteReview(ReviewCodeDTO reviewCodeDTO, String loginId);

    List<ReviewDTO> findReviewsWithFilters(Integer themeCode, String filter, Pageable pageable, String loginId);

    void likeReview(ReviewCodeDTO reviewCodeDTO, String loginId);

    void deleteLikeReview(ReviewCodeDTO reviewCodeDTO, String loginId);

    StatisticsReviewDTO findReviewStatistics(Integer themeCode);

    List<ReviewDTO> getReviewDTOS(List<Review> sublist, Integer memberCode);

    ReviewDTO getReviewDTO(Review review, Integer memberCode);

    ReviewReportDTO findReviewReposrt(String loginId);

    List<ReviewDTO> findReviewByMember(String loginId, Pageable pageable);

    ReviewDTO findReviewDetail(String loginId, Integer reviewCode);
}
