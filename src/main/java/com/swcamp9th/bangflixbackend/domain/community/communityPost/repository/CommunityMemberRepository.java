package com.swcamp9th.bangflixbackend.domain.community.communityPost.repository;

import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.CommunityMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Integer> {

}
