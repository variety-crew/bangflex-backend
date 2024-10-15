package com.swcamp9th.bangflixbackend.domain.community.comment.repository;

import com.swcamp9th.bangflixbackend.domain.community.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
