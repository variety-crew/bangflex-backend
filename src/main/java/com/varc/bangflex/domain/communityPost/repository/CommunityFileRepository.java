package com.varc.bangflex.domain.communityPost.repository;

import com.varc.bangflex.domain.communityPost.entity.CommunityFile;
import com.varc.bangflex.domain.communityPost.entity.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityFileRepository extends JpaRepository<CommunityFile, Integer> {

    List<CommunityFile> findByCommunityPost(CommunityPost post);

}
