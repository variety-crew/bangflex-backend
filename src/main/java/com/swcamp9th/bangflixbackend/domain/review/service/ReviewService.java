package com.swcamp9th.bangflixbackend.domain.review.service;

import com.swcamp9th.bangflixbackend.domain.review.dto.CreateReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.DeleteReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.ReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.UpdateReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.entity.Review;
import com.swcamp9th.bangflixbackend.domain.review.enums.Level;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ReviewService {

    void createReview(CreateReviewDTO newReview, List<MultipartFile> images) throws IOException;

    void updateReview(UpdateReviewDTO updateReview);

    void deleteReview(DeleteReviewDTO deleteReviewDTO);

    List<ReviewDTO> findReviewsWithFilters(Integer themeCode, String filter, Integer lastReviewCode);
}
