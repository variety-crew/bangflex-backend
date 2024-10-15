package com.swcamp9th.bangflixbackend.domain.ranking.entity;

import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewMember;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "member_ranking")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRanking {

    @Id
    @Column(name = "ranking_code")
    private Integer rankingCode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_code", nullable = false)
    private ReviewMember member;
}

