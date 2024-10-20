package com.swcamp9th.bangflixbackend.domain.comment.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentUpdateDTO {

    private String content;                 // 댓글 내용
}
