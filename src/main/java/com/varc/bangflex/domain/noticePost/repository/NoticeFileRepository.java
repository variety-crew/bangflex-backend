package com.varc.bangflex.domain.noticePost.repository;

import com.varc.bangflex.domain.noticePost.entity.NoticeFile;
import com.varc.bangflex.domain.noticePost.entity.NoticePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository("noticeFileRepository")
public interface NoticeFileRepository extends JpaRepository<NoticeFile, Integer> {

    Collection<NoticeFile> findByNoticePost(NoticePost noticePost);
}
