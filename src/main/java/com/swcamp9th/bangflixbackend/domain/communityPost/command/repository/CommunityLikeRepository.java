package com.swcamp9th.bangflixbackend.domain.communityPost.command.repository;

import com.swcamp9th.bangflixbackend.domain.communityPost.command.entity.CommunityLike;
import com.swcamp9th.bangflixbackend.domain.communityPost.command.entity.CommunityLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityLikeRepository extends JpaRepository<CommunityLike, CommunityLikeId> {

    boolean existsByMemberCodeAndCommunityPostCodeAndActiveTrue(Integer memberCode, Integer communityPostCode);

    List<CommunityLike> findByCommunityPostCodeAndActiveTrue(Integer communityPostCode);
}
