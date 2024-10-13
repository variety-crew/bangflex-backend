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
public class CommunityPostRequestDTO {

    private String title;                   // 제목
    private String content;                 // 내용
    private Integer memberCode;             // 회원 코드(작성자)

    //MultipartFile 형태로 파일 리스트 받기
    private List<MultipartFile> images;     // 첨부파일들

}
