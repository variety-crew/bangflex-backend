package com.swcamp9th.bangflixbackend.domain.review.repository;

import com.swcamp9th.bangflixbackend.domain.review.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT r FROM Review r JOIN FETCH r.member JOIN FETCH r.theme "
        + "WHERE r.theme.themeCode = :themeCode AND r.active = true")
    List<Review> findByThemeCodeAndActiveTrueWithFetchJoin(@Param("themeCode") Integer themeCode);


}
