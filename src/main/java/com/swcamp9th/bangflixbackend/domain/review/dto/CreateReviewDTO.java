package com.swcamp9th.bangflixbackend.domain.review.dto;

import com.swcamp9th.bangflixbackend.domain.review.enums.Activity;
import com.swcamp9th.bangflixbackend.domain.review.enums.Composition;
import com.swcamp9th.bangflixbackend.domain.review.enums.HorrorLevel;
import com.swcamp9th.bangflixbackend.domain.review.enums.Interior;
import com.swcamp9th.bangflixbackend.domain.review.enums.Level;
import com.swcamp9th.bangflixbackend.domain.review.enums.Probability;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateReviewDTO {
    private Integer themeCode;
    private Integer headcount;
    private Integer takenTime;
    private Integer totalScore;
    private Composition composition;
    private Level level;
    private HorrorLevel horrorLevel;
    private Activity activity;
    private Interior interior;
    private Probability probability;
    private String content;
    private Integer memberCode;
}
