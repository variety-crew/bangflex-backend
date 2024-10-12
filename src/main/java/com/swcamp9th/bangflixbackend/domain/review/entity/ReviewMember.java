package com.swcamp9th.bangflixbackend.domain.review.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_code")
    private Integer memberCode;

    @Column(name = "id", nullable = false, length = 255)
    private String id;

    @Column(name = "password", nullable = false, length = 1024)
    private String password;

    @Column(name = "nickname", nullable = false, length = 255)
    private String nickname;

    @Column(name = "email", nullable = false, length = 512)
    private String email;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    @Column(name = "image", length = 1024)
    private String image;

    @Column(name = "point", nullable = false)
    private Integer point = 0;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;

}

