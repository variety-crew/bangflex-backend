package com.varc.bangflex.domain.communityPost.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommunityPostCreateDTO {

    private String title;                   // 제목
    private String content;                 // 게시글 내용
}
