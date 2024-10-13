package com.swcamp9th.bangflixbackend.domain.review.repository;

import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewTendencyGenre;
import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewTendencyGenreId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewTendencyGenreRepository extends JpaRepository<ReviewTendencyGenre, ReviewTendencyGenreId> {

    List<ReviewTendencyGenre> findByTendency_TendencyCode(Integer tendencyCode);
}
