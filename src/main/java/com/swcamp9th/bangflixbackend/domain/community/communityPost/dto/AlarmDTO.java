package com.swcamp9th.bangflixbackend.domain.community.communityPost.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AlarmDTO {     // comment에 대한 내용
    private Integer postId;
    private String writer;
    private String content;
    private LocalDateTime createdAt;
}
