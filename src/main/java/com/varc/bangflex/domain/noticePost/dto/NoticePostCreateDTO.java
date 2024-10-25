package com.varc.bangflex.domain.noticePost.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NoticePostCreateDTO {

    private String title;                   // 제목
    private String content;                 // 게시글 내용
}
