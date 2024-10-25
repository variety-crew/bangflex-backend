package com.varc.bangflex.domain.review.dto;

import com.varc.bangflex.domain.review.enums.Activity;
import com.varc.bangflex.domain.review.enums.Composition;
import com.varc.bangflex.domain.review.enums.HorrorLevel;
import com.varc.bangflex.domain.review.enums.Interior;
import com.varc.bangflex.domain.review.enums.Level;
import com.varc.bangflex.domain.review.enums.Probability;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateReviewDTO {
    private Integer reviewCode;
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
}
