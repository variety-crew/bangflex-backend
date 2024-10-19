package com.swcamp9th.bangflixbackend.domain.community.communityPost.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommunityLikeDTO {

    private Integer memberCode;             // 회원 코드
    private Integer communityPostCode;      // 게시글 코드
}
