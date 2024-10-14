package com.swcamp9th.bangflixbackend.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatisticsReviewDTO {
    private Double avgTotalScore;
    private int tenScorePercent;
    private int eightScorePercent;
    private int sixScorePercent;
    private int fourScorePercent;
    private int twoScorePercent;

    private int oneLevelPercent;
    private int twoLevelPercent;
    private int threeLevelPercent;
    private int fourLevelPercent;
    private int fiveLevelPercent;

    private int oneHorrorLevelPercent;
    private int twoHorrorLevelPercent;
    private int threeHorrorLevelPercent;
    private int fourHorrorLevelPercent;
    private int fiveHorrorLevelPercent;

    private int oneActivePercent;
    private int twoActivePercent;
    private int threeActivePercent;
    private int fourActivePercent;
    private int fiveActivePercent;

    private int oneInteriorPercent;
    private int twoInteriorPercent;
    private int threeInteriorPercent;
    private int fourInteriorPercent;
    private int fiveInteriorPercent;

    private int oneProbabilityPercent;
    private int twoProbabilityPercent;
    private int threeProbabilityPercent;
    private int fourProbabilityPercent;
    private int fiveProbabilityPercent;

    private int oneCompositionPercent;
    private int twoCompositionPercent;
    private int threeCompositionPercent;
    private int fourCompositionPercent;
    private int fiveCompositionPercent;
}
