package com.varc.bangflex.domain.noticePost.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NoticePostUpdateDTO {

    private String title;                   // 제목
    private String content;                 // 게시글 내용

//    // 첨부파일 URL 리스트
//    private List<String> imageUrls;         // 첨부파일들
}
