package com.swcamp9th.bangflixbackend.domain.community.comment.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentCreateDTO {

    private String content;                 // 댓글 내용
    private Integer memberCode;             // 회원 코드(작성자)
}
