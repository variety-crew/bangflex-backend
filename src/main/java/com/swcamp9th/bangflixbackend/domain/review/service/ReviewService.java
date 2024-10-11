package com.swcamp9th.bangflixbackend.domain.review.service;

import com.swcamp9th.bangflixbackend.domain.review.dto.CreateReviewDTO;

public interface ReviewService {

    void createReview(CreateReviewDTO newReview);
}
