package com.swcamp9th.bangflixbackend.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "member")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_code", nullable = false)
    private Integer memberCode;

    @Column(nullable = false, length = 255)
    private String id;

    @Column(nullable = false, length = 1024)
    private String password;

    @Column(nullable = false, length = 255)
    private String nickname;

    @Column(nullable = false, length = 512)
    private String email;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    @Column(nullable = true, length = 1024)
    private String image;

    @Column(nullable = false)
    private Integer point;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = false)
    private Boolean active;

    @Builder
    public Member(String id, String password, String nickname, String email, Boolean isAdmin, String image) {
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.isAdmin = isAdmin;
        this.image = image;
        point = 0;
        createdAt = new Date();
        active = true;
    }

    public MemberRoleEnum getRole() {
        return isAdmin ? MemberRoleEnum.ADMIN : MemberRoleEnum.USER;
    }
}
