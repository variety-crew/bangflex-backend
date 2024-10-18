package com.swcamp9th.bangflixbackend.domain.eventPost.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EventPostDTO {

    private Integer eventPostCode;          // 이벤트 게시글 코드
    private Boolean active;                 // 활성화 여부
    private LocalDateTime createdAt;        // 생성일시(작성일시)
    private String title;                   // 제목
    private String content;                 // 게시글 내용
    private String category;                // 카테고리(할인테마/신규테마)
    private Integer memberCode;             // 회원 코드(작성자)

    // 첨부파일 URL 리스트
    private List<String> imageUrls;         // 첨부파일들

    // 이벤트 해당 테마
    private EventThemeDTO eventTheme;
}
