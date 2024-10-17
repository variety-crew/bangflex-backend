package com.swcamp9th.bangflixbackend.domain.eventPost.service;

import com.swcamp9th.bangflixbackend.domain.eventPost.dto.EventPostCreateDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EventPostService {

    void createEvent(String loginId, EventPostCreateDTO newEvent, List<MultipartFile> images) throws IOException;
}
