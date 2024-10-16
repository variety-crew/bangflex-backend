package com.swcamp9th.bangflixbackend.domain.noticePost.repository;

import com.swcamp9th.bangflixbackend.domain.noticePost.entity.NoticeFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("noticeFileRepository")
public interface NoticeFileRepository extends JpaRepository<NoticeFile, Integer> {
}
