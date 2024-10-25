package com.varc.bangflex.domain.store.service;

import com.varc.bangflex.domain.review.dto.ReviewDTO;
import com.varc.bangflex.domain.store.dto.StoreDTO;

public interface StoreService {

    StoreDTO findStore(Integer storeCode);

    ReviewDTO findBestReviewByStore(Integer storeCode, String loginId);
}
