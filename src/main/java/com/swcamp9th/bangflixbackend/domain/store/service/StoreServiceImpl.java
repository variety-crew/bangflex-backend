package com.swcamp9th.bangflixbackend.domain.store.service;

import com.swcamp9th.bangflixbackend.domain.review.dto.ReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.entity.Review;
import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewLike;
import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewTheme;
import com.swcamp9th.bangflixbackend.domain.review.repository.ReviewLikeRepository;
import com.swcamp9th.bangflixbackend.domain.review.repository.ReviewRepository;
import com.swcamp9th.bangflixbackend.domain.review.repository.ReviewThemeRepository;
import com.swcamp9th.bangflixbackend.domain.review.service.ReviewService;
import com.swcamp9th.bangflixbackend.domain.store.dto.StoreDTO;
import com.swcamp9th.bangflixbackend.domain.store.repository.StoreRepository;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService {

    private StoreRepository storeRepository;
    private ModelMapper modelMapper;
    private ReviewLikeRepository reviewLikeRepository;
    private ReviewRepository reviewRepository;
    private ReviewService reviewService;

    @Autowired
    public StoreServiceImpl(StoreRepository storeRepository
                          , ModelMapper modelMapper
                          , ReviewLikeRepository reviewLikeRepository
                          , ReviewRepository reviewRepository
                          , ReviewService reviewService) {
        this.storeRepository = storeRepository;
        this.modelMapper = modelMapper;
        this.reviewLikeRepository = reviewLikeRepository;
        this.reviewRepository = reviewRepository;
        this.reviewService = reviewService;
    }

    @Override
    public StoreDTO findStroe(Integer storeCode) {
        return modelMapper.map(storeRepository.findById(storeCode), StoreDTO.class);
    }

    @Override
    public ReviewDTO findBestReviewByStroe(Integer storeCode) {
        List<ReviewLike> reviewLike = reviewLikeRepository.findBestReviewByStoreCode(storeCode);

        if(reviewLike.isEmpty())
            return null;

        Review review = reviewRepository.findById(reviewLike.get(0).getReviewCode()).orElse(null);

        return reviewService.getReviewDTO(review);
    }
}
