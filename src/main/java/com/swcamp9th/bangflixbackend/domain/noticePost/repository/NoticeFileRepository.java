package com.swcamp9th.bangflixbackend.domain.noticePost.repository;

import com.swcamp9th.bangflixbackend.domain.noticePost.entity.NoticeFile;
import com.swcamp9th.bangflixbackend.domain.noticePost.entity.NoticePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository("noticeFileRepository")
public interface NoticeFileRepository extends JpaRepository<NoticeFile, Integer> {

    Collection<NoticeFile> findByNoticePost(NoticePost noticePost);
}
