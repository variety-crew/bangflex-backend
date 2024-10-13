package com.swcamp9th.bangflixbackend.domain.review.repository;

import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewTendency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewTendencyRepository extends JpaRepository<ReviewTendency, Integer> {

    ReviewTendency findByMember_MemberCode(Integer memberCode);
}
