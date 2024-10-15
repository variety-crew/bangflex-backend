package com.swcamp9th.bangflixbackend.domain.review.service;

import com.swcamp9th.bangflixbackend.domain.review.dto.CreateReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.ReviewCodeDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.ReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.StatisticsReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.UpdateReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.entity.Review;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ReviewService {

    void createReview(CreateReviewDTO newReview, List<MultipartFile> images)
        throws IOException, URISyntaxException;

    void updateReview(UpdateReviewDTO updateReview);

    void deleteReview(ReviewCodeDTO reviewCodeDTO);

    List<ReviewDTO> findReviewsWithFilters(Integer themeCode, String filter, Pageable pageable);

    void likeReview(ReviewCodeDTO reviewCodeDTO);

    void deleteLikeReview(ReviewCodeDTO reviewCodeDTO);

    StatisticsReviewDTO findReviewStatistics(Integer themeCode);

    List<ReviewDTO> getReviewDTOS(List<Review> sublist);

    ReviewDTO getReviewDTO(Review review);
}
