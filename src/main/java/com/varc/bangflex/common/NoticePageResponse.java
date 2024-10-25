package com.varc.bangflex.common;

import com.varc.bangflex.domain.noticePost.dto.NoticePostDTO;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NoticePageResponse {

    private List<NoticePostDTO> noticePosts;
    private int currentPage;
    private int totalPages;
    private long totalElements;
}
