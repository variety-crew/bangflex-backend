package com.swcamp9th.bangflixbackend.domain.review.repository;

import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewTheme;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewThemeRepository extends JpaRepository<ReviewTheme, Integer> {

}
