package com.swcamp9th.bangflixbackend.domain.community.communityPost.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommunityMember {      // 시큐리티 적용 후 삭제 예정

    @Id
    @Column(name = "member_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberCode;

    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    @Column(name = "image")
    private String image;

    @Column(name = "point", nullable = false)
    private Integer point = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;
}
