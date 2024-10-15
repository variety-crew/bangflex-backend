package com.swcamp9th.bangflixbackend.domain.community.comment.repository;

import com.swcamp9th.bangflixbackend.domain.community.comment.entity.Comment;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByCommunityPostAndActiveTrue(CommunityPost foundPost);
}
