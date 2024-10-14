package com.swcamp9th.bangflixbackend.domain.ranking.controller;

import com.swcamp9th.bangflixbackend.common.ResponseMessage;
import com.swcamp9th.bangflixbackend.domain.ranking.service.RankingService;
import com.swcamp9th.bangflixbackend.domain.review.dto.StatisticsReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/rankings")
public class RankingController {

    private final RankingService rankingService;

    @Autowired
    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @PostMapping("/test")
    public ResponseEntity<ResponseMessage<Object>> testCreateTop5Review() {

        rankingService.createReviewRanking();

        return ResponseEntity.ok(new ResponseMessage<>(200, "Top5 리뷰 생성", null));
    }

    @GetMapping("")
    public ResponseEntity<ResponseMessage<StatisticsReviewDTO>> findReviewRankingDate() {

        rankingService.findReviewRankingDate();

        return ResponseEntity.ok(new ResponseMessage<>(200, "Top5 리뷰 생성", null));
    }
}
