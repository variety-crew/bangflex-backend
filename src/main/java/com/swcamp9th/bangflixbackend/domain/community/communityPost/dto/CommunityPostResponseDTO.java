package com.swcamp9th.bangflixbackend.domain.community.communityPost.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommunityPostResponseDTO {

    private Integer communityPostCode;      // 게시글 코드
    private String title;                   // 제목
    private LocalDateTime createdAt;        // 생성일시(작성일시)
    private Integer memberCode;             // 회원 코드(작성자)
}
