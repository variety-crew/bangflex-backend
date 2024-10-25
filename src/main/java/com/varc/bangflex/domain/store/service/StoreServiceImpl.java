package com.varc.bangflex.domain.store.service;

import com.varc.bangflex.domain.review.dto.ReviewDTO;
import com.varc.bangflex.domain.review.entity.Review;
import com.varc.bangflex.domain.review.entity.ReviewLike;
import com.varc.bangflex.domain.review.repository.ReviewLikeRepository;
import com.varc.bangflex.domain.review.repository.ReviewRepository;
import com.varc.bangflex.domain.review.service.ReviewService;
import com.varc.bangflex.domain.store.dto.StoreDTO;
import com.varc.bangflex.domain.store.entity.Store;
import com.varc.bangflex.domain.store.repository.StoreRepository;
import com.varc.bangflex.domain.user.entity.Member;
import com.varc.bangflex.domain.user.repository.UserRepository;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final ModelMapper modelMapper;
    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;
    private final UserRepository userRepository;

    @Autowired
    public StoreServiceImpl(StoreRepository storeRepository
                          , ModelMapper modelMapper
                          , ReviewLikeRepository reviewLikeRepository
                          , ReviewRepository reviewRepository
                          , ReviewService reviewService
                          , UserRepository userRepository) {
        this.storeRepository = storeRepository;
        this.modelMapper = modelMapper;
        this.reviewLikeRepository = reviewLikeRepository;
        this.reviewRepository = reviewRepository;
        this.reviewService = reviewService;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public StoreDTO findStore(Integer storeCode) {
        Store store = storeRepository.findById(storeCode).orElseThrow();
        return modelMapper.map(store, StoreDTO.class);
    }

    @Override
    @Transactional
    public ReviewDTO findBestReviewByStore(Integer storeCode, String loginId) {
        List<ReviewLike> reviewLike = reviewLikeRepository.findBestReviewByStoreCode(storeCode);
        Member member = userRepository.findById(loginId).orElseThrow();

        if(reviewLike.isEmpty())
            return null;

        Review review = reviewRepository.findById(reviewLike.get(0).getReviewCode()).orElse(null);

        return reviewService.getReviewDTO(review, member.getMemberCode());
    }
}
