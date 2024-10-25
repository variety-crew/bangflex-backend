package com.varc.bangflex.domain.eventPost.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "event_file")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EventFile {

    @Id
    @Column(name = "event_file_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventFileCode;

    @Column(name = "url")
    private String url;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_post_code", nullable = false)
    private EventPost eventPost;
}
