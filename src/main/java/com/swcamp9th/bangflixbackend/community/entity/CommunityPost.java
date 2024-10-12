package com.swcamp9th.bangflixbackend.community.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Table(name = "community_post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommunityPost {

    @Id
    @Column(name = "community_post_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int communityPostCode;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    @ColumnDefault("TRUE")
    private Boolean active;

//    @ManyToOne
//    @JoinColumn(name = "member_code")
    @Column(name = "member_code")
    private Integer memberCode;
}
