package com.swcamp9th.bangflixbackend.domain.communityPost.command.repository;

import com.swcamp9th.bangflixbackend.domain.communityPost.command.entity.CommunityFile;
import com.swcamp9th.bangflixbackend.domain.communityPost.command.entity.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityFileRepository extends JpaRepository<CommunityFile, Integer> {

    List<CommunityFile> findByCommunityPost(CommunityPost post);

}
