package com.swcamp9th.bangflixbackend.domain.community.communityPost.repository;

import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.CommunityFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityFileRepository extends JpaRepository<CommunityFile, Integer> {
}
