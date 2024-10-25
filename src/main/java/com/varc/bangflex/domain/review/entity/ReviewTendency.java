package com.varc.bangflex.domain.review.entity;


import com.varc.bangflex.domain.user.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tendency")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewTendency {

    @Id
    @Column(name = "tendency_code", nullable = false)
    private Integer tendencyCode;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "be_skilled", nullable = false)
    private BeSkilled beSkilled;

    @Enumerated(EnumType.STRING)
    @Column(name = "situation")
    private Situation situation;

    @Column(name = "element", nullable = false)
    private String element;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;

    // Foreign keys
    @OneToOne
    @JoinColumn(name = "member_code", nullable = false)
    private Member member;

    public enum BeSkilled {
        CHILDREN,
        BEGINNERS,
        INTERMEDIATE,
        ADVANCED
    }

    public enum Situation {
        SINGLE,
        FRIEND,
        COUPLE,
        STRANGER,
        CHALLENGER
    }

}
