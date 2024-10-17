package com.swcamp9th.bangflixbackend.domain.noticePost.repository;

import com.swcamp9th.bangflixbackend.domain.noticePost.dto.NoticePostDTO;
import com.swcamp9th.bangflixbackend.domain.noticePost.entity.NoticePost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("noticePostRepository")
public interface NoticePostRepository extends JpaRepository<NoticePost, Integer> {

    Page<NoticePost> findByActiveTrue(Pageable pageable);
}
