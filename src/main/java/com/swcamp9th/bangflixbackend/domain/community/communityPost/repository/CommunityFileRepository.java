package com.swcamp9th.bangflixbackend.domain.community.communityPost.repository;

import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.CommunityFile;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityFileRepository extends JpaRepository<CommunityFile, Integer> {

    List<CommunityFile> findByCommunityPost(CommunityPost post);

}
