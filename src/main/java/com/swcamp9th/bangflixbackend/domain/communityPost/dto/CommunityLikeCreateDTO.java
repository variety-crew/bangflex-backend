package com.swcamp9th.bangflixbackend.domain.communityPost.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommunityLikeCreateDTO {

    private Integer communityPostCode;      // 게시글 코드
}
