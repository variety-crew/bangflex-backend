package com.swcamp9th.bangflixbackend.domain.community.communityPost.repository;

import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityPostRepository extends JpaRepository<CommunityPost, Integer> {
}
