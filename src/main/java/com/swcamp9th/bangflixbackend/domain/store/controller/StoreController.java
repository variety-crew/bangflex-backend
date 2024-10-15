package com.swcamp9th.bangflixbackend.domain.store.controller;

import com.swcamp9th.bangflixbackend.common.ResponseMessage;
import com.swcamp9th.bangflixbackend.domain.review.dto.ReviewDTO;
import com.swcamp9th.bangflixbackend.domain.store.dto.StoreDTO;
import com.swcamp9th.bangflixbackend.domain.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stores")
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/{storeCode}")
    public ResponseEntity<ResponseMessage<StoreDTO>> findStore(
        @PathVariable("storeCode") Integer storeCode) {

        // 서비스에서 필터를 사용해 조회
        StoreDTO store = storeService.findStroe(storeCode);

        return ResponseEntity.ok(new ResponseMessage<>(200, storeCode + "번 업체 조회 성공", store));
    }

    @GetMapping("/bestreview/{storeCode}")
    public ResponseEntity<ResponseMessage<ReviewDTO>> findBestReviewByStore(
        @PathVariable("storeCode") Integer storeCode) {

        // 서비스에서 필터를 사용해 조회
        ReviewDTO storeBestReview  = storeService.findBestReviewByStroe(storeCode);

        return ResponseEntity.ok(new ResponseMessage<>(200, storeCode + "번 업체 베스트 리뷰 조회 성공", storeBestReview));
    }
}
