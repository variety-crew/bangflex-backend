package com.swcamp9th.bangflixbackend.domain.review.repository;

import com.swcamp9th.bangflixbackend.domain.theme.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewThemeRepository extends JpaRepository<Theme, Integer> {

}
