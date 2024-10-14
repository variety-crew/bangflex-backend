package com.swcamp9th.bangflixbackend.domain.ranking.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//public class ReviewRankingDTO {
//
//    private Integer reviewCode;
//    private Boolean active;
//    private String createdAt;
//    private Integer headcount;
//    private Integer takenTime;
//    private String composition;
//    private String level;
//    private String horrorLevel;
//    private String activity;
//    private Integer totalScore;
//    private String interior;
//    private String probability;
//    private String content;
//    private Integer memberCode;
//    private Integer themeCode;
//}


public interface ReviewRankingDTO {

    Integer getReviewCode();
    Boolean getActive();
    String getCreatedAt();
    Integer getHeadcount();
    Integer getTakenTime();
    String getComposition();
    String getLevel();
    String getHorrorLevel();
    String getActivity();
    Integer getTotalScore();
    String getInterior();
    String getProbability();
    String getContent();
    Integer getMemberCode();
    Integer getThemeCode();
}
