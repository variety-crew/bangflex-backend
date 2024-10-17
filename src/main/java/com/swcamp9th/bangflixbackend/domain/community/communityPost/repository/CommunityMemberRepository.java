package com.swcamp9th.bangflixbackend.domain.community.communityPost.repository;

import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.CommunityMember;
import org.springframework.data.jpa.repository.JpaRepository;
// 시큐리티 적용 후 삭제예정
public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Integer> {
}
