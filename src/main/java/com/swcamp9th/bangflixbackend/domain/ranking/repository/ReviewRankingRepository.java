package com.swcamp9th.bangflixbackend.domain.ranking.repository;

import com.swcamp9th.bangflixbackend.domain.ranking.entity.ReviewRanking;
import com.swcamp9th.bangflixbackend.domain.review.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRankingRepository extends JpaRepository<ReviewRanking, Integer> {

    @Query("SELECT DISTINCT FUNCTION('DATE_FORMAT', rr.createdAt, '%Y-%m-%d') " +
        "FROM ReviewRanking rr " +
        "WHERE YEAR(rr.createdAt) = :year")
    List<String> findDistinctDatesByYear(@Param("year") int year);

    @Query("SELECT rr.review FROM ReviewRanking rr JOIN FETCH rr.review " +
        "WHERE FUNCTION('DATE', rr.createdAt) = FUNCTION('STR_TO_DATE', :date, '%Y-%m-%d')")
    List<Review> findByCreatedAtDate(@Param("date") String date);

}
