package com.swcamp9th.bangflixbackend.domain.review.repository;

import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewGenreRepository extends JpaRepository<ReviewGenre, Integer> {

}
