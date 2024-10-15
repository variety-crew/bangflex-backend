package com.swcamp9th.bangflixbackend.domain.community.comment.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentDTO {

    private Integer commentCode;
    private Boolean active;
    private LocalDateTime createdAt;
    private String content;
    private Integer memberCode;
    private Integer communityPostCode;
}
