package com.varc.bangflex.domain.comment.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentCreateDTO {

    private String content;                 // 댓글 내용
}
