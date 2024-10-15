package com.swcamp9th.bangflixbackend.domain.community.comment.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentDTO {

    private Integer commentCode;            // 댓글 코드
    private Boolean active;                 // 활성화 여부
    private LocalDateTime createdAt;        // 생성일시(작성일시)
    private String content;                 // 댓글 내용
    private Integer memberCode;             // 회원 코드(작성자)
    private Integer communityPostCode;      // 게시글 코드(댓글이 작성된 게시글)
}
