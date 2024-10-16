package com.swcamp9th.bangflixbackend.domain.noticePost.service;

import com.swcamp9th.bangflixbackend.domain.noticePost.dto.NoticePostCreateDTO;
import com.swcamp9th.bangflixbackend.domain.noticePost.dto.NoticePostUpdateDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NoticePostService {

    void createNoticePost(NoticePostCreateDTO newNotice, List<MultipartFile> images, String userId) throws IOException;

    void updateNoticePost(int noticePostCode, NoticePostUpdateDTO updatedNotice,
                          List<MultipartFile> images, String userId);

    void deleteNoticePost(int noticePostCode, String userId);

}
