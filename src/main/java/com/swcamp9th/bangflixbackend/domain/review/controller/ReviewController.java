package com.swcamp9th.bangflixbackend.domain.review.controller;

import com.swcamp9th.bangflixbackend.common.ResponseMessage;
import com.swcamp9th.bangflixbackend.domain.review.dto.CreateReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.ReviewCodeDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.ReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.ReviewReportDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.StatisticsReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.UpdateReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.service.ReviewService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ResponseMessage<Object>> createReview(
        @RequestPart("review") CreateReviewDTO newReview,
        @RequestPart(value = "images", required = false) List<MultipartFile> images,
        @RequestAttribute("loginId") String loginId)
        throws IOException, URISyntaxException {

        reviewService.createReview(newReview, images, loginId);

        return ResponseEntity.ok(new ResponseMessage<>(200, "리뷰 작성 성공", null));
    }

    @PutMapping("")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ResponseMessage<Object>> updateReview(@RequestBody UpdateReviewDTO updateReview,
        @RequestAttribute("loginId") String loginId)
        throws IOException {
        
        reviewService.updateReview(updateReview, loginId);

        return ResponseEntity.ok(new ResponseMessage<>(200, "리뷰 수정 성공", null));
    }

    @DeleteMapping("")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ResponseMessage<Object>> deleteReview(@RequestBody ReviewCodeDTO reviewCodeDTO,
        @RequestAttribute("loginId") String loginId) {

        reviewService.deleteReview(reviewCodeDTO, loginId);

        return ResponseEntity.ok(new ResponseMessage<>(200, "리뷰 삭제 성공", null));
    }

    @GetMapping("/{themeCode}")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ResponseMessage<List<ReviewDTO>>> findReviewList(
        @PathVariable("themeCode") Integer themeCode,
        @PageableDefault(size = 10) Pageable pageable,
        @RequestParam(required = false) String filter) {
        /*
            필터 값은 필수 X.
            필터 값이 없다면 기본 최신순 리뷰 정렬
            highScore, lowScore 값 중 하나를 string 형태로 주면
            점수 별 정렬. 점수가 같다면 날짜 순 정렬

            화면을 참고해보니 더보기 버튼 클릭. 즉, 페이지네이션 X.
            요청 시, 초기에 10개씩 보냄. 이후, lastReviewCode를 주면 전체 정렬 중 그 이후 10개를 보내줌
        */

        // 서비스에서 필터를 사용해 조회
        List<ReviewDTO> reviews = reviewService.findReviewsWithFilters(themeCode, filter, pageable);

        return ResponseEntity.ok(new ResponseMessage<>(200, "리뷰 조회 성공", reviews));
    }

    @GetMapping("/statistics/{themeCode}")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ResponseMessage<StatisticsReviewDTO>> findReviewStatistics(
        @PathVariable("themeCode") Integer themeCode) {

        // 서비스에서 필터를 사용해 조회
        StatisticsReviewDTO reviewStatistics = reviewService.findReviewStatistics(themeCode);

        return ResponseEntity.ok(new ResponseMessage<>(200, "리뷰 통계 조회 성공", reviewStatistics));
    }

    @PostMapping("/likes")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ResponseMessage<Object>> likeReview(@RequestBody ReviewCodeDTO reviewCodeDTO,
        @RequestAttribute("loginId") String loginId) {

        reviewService.likeReview(reviewCodeDTO, loginId);

        return ResponseEntity.ok(new ResponseMessage<>(200, "리뷰 좋아요 성공", null));
    }

    @DeleteMapping("/likes")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ResponseMessage<Object>> deleteLikeReview(@RequestBody ReviewCodeDTO reviewCodeDTO,
        @RequestAttribute("loginId") String loginId) {

        reviewService.deleteLikeReview(reviewCodeDTO, loginId);

        return ResponseEntity.ok(new ResponseMessage<>(200, "리뷰 좋아요 취소 성공", null));
    }

    @GetMapping("/user/report")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ResponseMessage<ReviewReportDTO>> findReviewReport(
        @RequestAttribute("loginId") String loginId
    ) {

        // 서비스에서 필터를 사용해 조회
        ReviewReportDTO reviewReportDTO = reviewService.findReviewReposrt(loginId);

        return ResponseEntity.ok(new ResponseMessage<>(200, "유저 리뷰 report 조회 성공", reviewReportDTO));
    }

    @GetMapping("/user")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ResponseMessage<List<ReviewDTO>>> findReviewByMember(
        @RequestAttribute("loginId") String loginId,
        @PageableDefault(size = 10) Pageable pageable
    ) {

        // 서비스에서 필터를 사용해 조회
        List<ReviewDTO> reviews = reviewService.findReviewByMember(loginId, pageable);

        return ResponseEntity.ok(new ResponseMessage<>(200, "유저가 작성한 리뷰 조회 성공", reviews));
    }
}
