package com.swcamp9th.bangflixbackend.domain.review.controller;

import com.swcamp9th.bangflixbackend.common.ResponseMessage;
import com.swcamp9th.bangflixbackend.domain.ex.dto.ExDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.CreateReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.UpdateReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.service.ReviewService;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping("api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @PostMapping("")
    public ResponseEntity<ResponseMessage<Object>> createReview(
        @RequestPart("review") CreateReviewDTO newReview,
        @RequestPart(value = "images", required = false) List<MultipartFile> images)
        throws IOException {

        reviewService.createReview(newReview, images);

        return ResponseEntity.ok(new ResponseMessage<>(200, "리뷰 작성 성공", null));
    }

    @PutMapping("")
    public ResponseEntity<ResponseMessage<Object>> updateReview(
        @RequestPart("review") UpdateReviewDTO updateReview,
        @RequestPart(value = "images", required = false) List<MultipartFile> images)
        throws IOException {
        
        reviewService.updateReview(updateReview, images);

        return ResponseEntity.ok(new ResponseMessage<>(200, "리뷰 수정 성공", null));
    }

}
