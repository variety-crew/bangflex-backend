package com.varc.bangflex.domain.review.entity;

import com.varc.bangflex.domain.review.enums.Activity;
import com.varc.bangflex.domain.review.enums.Composition;
import com.varc.bangflex.domain.review.enums.HorrorLevel;
import com.varc.bangflex.domain.review.enums.Interior;
import com.varc.bangflex.domain.review.enums.Level;
import com.varc.bangflex.domain.review.enums.Probability;
import com.varc.bangflex.domain.theme.entity.Theme;
import com.varc.bangflex.domain.user.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_code", nullable = false)
    private Integer reviewCode;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "headcount", nullable = false)
    private Integer headcount;

    @Column(name = "taken_time")
    private Integer takenTime; // 분 단위로 기록

    @Enumerated(EnumType.STRING)
    @Column(name = "composition", nullable = false)
    private Composition composition;

    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private Level level;

    @Enumerated(EnumType.STRING)
    @Column(name = "horror_level", nullable = false)
    private HorrorLevel horrorLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity", nullable = false)
    private Activity activity;

    @Column(name = "total_score", nullable = false)
    private Integer totalScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "interior", nullable = false)
    private Interior interior;

    @Enumerated(EnumType.STRING)
    @Column(name = "probability")
    private Probability probability;

    @Column(name = "content", length = 1024, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_code", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_code", nullable = false)
    private Theme theme;

}

