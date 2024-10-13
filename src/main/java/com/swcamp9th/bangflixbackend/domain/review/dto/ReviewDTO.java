package com.swcamp9th.bangflixbackend.domain.review.dto;

import java.time.LocalDateTime;
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
public class ReviewDTO {

    private Integer reviewCode;
    private Boolean active;
    private String createdAt;
    private Integer headcount;
    private Integer takenTime;
    private String composition;
    private String level;
    private String horrorLevel;
    private String activity;
    private Integer totalScore;
    private String interior;
    private String probability;
    private String content;
    private Integer memberCode;
    private Integer themeCode;
}
