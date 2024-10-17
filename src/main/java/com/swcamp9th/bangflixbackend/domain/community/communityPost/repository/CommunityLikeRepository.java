package com.swcamp9th.bangflixbackend.domain.community.communityPost.repository;

import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.CommunityLike;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.CommunityLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityLikeRepository extends JpaRepository<CommunityLike, CommunityLikeId> {

    boolean existsByMemberCodeAndCommunityPostCodeAndActiveTrue(Integer memberCode, Integer communityPostCode);
}
