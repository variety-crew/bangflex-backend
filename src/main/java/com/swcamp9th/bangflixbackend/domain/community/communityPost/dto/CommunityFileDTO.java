package com.swcamp9th.bangflixbackend.domain.community.communityPost.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommunityFileDTO {

    private Integer communityFileCode;
    private String url;
    private LocalDateTime createdAt;
    private Boolean active;
    private Integer communityPostCode;
}
