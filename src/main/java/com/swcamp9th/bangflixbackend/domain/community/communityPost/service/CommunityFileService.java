package com.swcamp9th.bangflixbackend.domain.community.communityPost.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CommunityFileService {

    List<String> uploadFiles(Integer communityPostCode, List<MultipartFile> images) throws IOException;
}
