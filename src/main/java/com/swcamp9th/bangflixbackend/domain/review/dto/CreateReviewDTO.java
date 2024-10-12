package com.swcamp9th.bangflixbackend.domain.review.dto;

import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewMember;
import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewTheme;
import com.swcamp9th.bangflixbackend.domain.review.enums.Activity;
import com.swcamp9th.bangflixbackend.domain.review.enums.Composition;
import com.swcamp9th.bangflixbackend.domain.review.enums.HorrorLevel;
import com.swcamp9th.bangflixbackend.domain.review.enums.Interior;
import com.swcamp9th.bangflixbackend.domain.review.enums.Level;
import com.swcamp9th.bangflixbackend.domain.review.enums.Probability;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
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
