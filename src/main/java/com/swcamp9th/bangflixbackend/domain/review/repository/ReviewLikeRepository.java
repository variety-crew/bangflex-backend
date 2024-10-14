package com.swcamp9th.bangflixbackend.domain.review.repository;

import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewFile;
import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewLike;
import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewLikeId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewLikeRepository extends JpaRepository<ReviewLike, ReviewLikeId> {

    @Query("SELECT r FROM ReviewLike r JOIN FETCH r.review JOIN FETCH r.member "
        + "WHERE r.review.reviewCode = :reviewCode AND r.member.memberCode = :memberCode AND r.active = true")
    ReviewLike findByMemberCodeAndReviewCode(@Param("memberCode") Integer memberCode, @Param("reviewCode") Integer reviewCode);

    @Query("SELECT r FROM ReviewLike r JOIN FETCH r.review JOIN FETCH r.member "
        + "WHERE r.review.reviewCode = :reviewCode AND r.active = true")
    List<ReviewLike> findByReviewCode(@Param("reviewCode")Integer reviewCode);
}