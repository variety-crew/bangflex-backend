package com.varc.bangflex.domain.eventPost.entity;

import com.varc.bangflex.domain.theme.entity.Theme;
import com.varc.bangflex.domain.user.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "event_post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventPost {

    @Id
    @Column(name = "event_post_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventPostCode;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "category", nullable = false)
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_code", nullable = false)
    private Theme theme;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_code", nullable = false)
    private Member member;

    // 첨부파일과의 관계 설정
    @OneToMany(mappedBy = "eventPost", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<EventFile> eventFiles = new ArrayList<>();
}
