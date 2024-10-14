package com.swcamp9th.bangflixbackend.domain.review.repository;

import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewMemberRepository extends JpaRepository<ReviewMember, Integer> {

}
