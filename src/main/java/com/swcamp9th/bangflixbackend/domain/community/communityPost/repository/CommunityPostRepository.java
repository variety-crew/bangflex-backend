package com.swcamp9th.bangflixbackend.domain.community.communityPost.repository;

import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.CommunityPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("communityPostRepository")
public interface CommunityPostRepository extends JpaRepository<CommunityPost, Integer> {

    Page<CommunityPost> findByActiveTrue(Pageable pageable);

    List<CommunityPost> findByActiveTrue(Sort createdAt);
}
