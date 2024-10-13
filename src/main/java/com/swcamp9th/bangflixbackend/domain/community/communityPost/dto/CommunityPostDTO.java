package com.swcamp9th.bangflixbackend.domain.community.communityPost.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommunityPostDTO {

    private Integer communityPostCode;      // 게시글 코드
    private String title;                   // 제목
    private String Content;                 // 내용
    private LocalDateTime createdAt;        // 생성일시(작성일시)
    private Boolean active;                 // 활성화 여부
    private Integer memberCode;             // 회원 코드(작성자)

    private List<MultipartFile> images;     // 첨부파일들
}
