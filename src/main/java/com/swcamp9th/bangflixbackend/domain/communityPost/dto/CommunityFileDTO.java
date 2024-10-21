package com.swcamp9th.bangflixbackend.domain.communityPost.dto;

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
    private String createdAt;
    private Boolean active;
    private Integer communityPostCode;
}
