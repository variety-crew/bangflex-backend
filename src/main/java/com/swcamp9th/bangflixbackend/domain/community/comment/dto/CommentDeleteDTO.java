package com.swcamp9th.bangflixbackend.domain.community.comment.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentDeleteDTO {

    private Integer commentCode;            // 댓글 코드
    private Integer memberCode;             // 회원 코드(작성자)
}
