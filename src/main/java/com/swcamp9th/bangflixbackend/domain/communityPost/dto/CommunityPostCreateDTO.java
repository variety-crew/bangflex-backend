package com.swcamp9th.bangflixbackend.domain.communityPost.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommunityPostCreateDTO {

    private String title;                   // 제목
    private String content;                 // 게시글 내용
}
