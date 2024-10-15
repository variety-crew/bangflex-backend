package com.swcamp9th.bangflixbackend.domain.community.comment.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentRequestDTO {

    private String content;                 // 댓글 내용
    private Integer memberCode;             // 회원 코드(작성자)
}
