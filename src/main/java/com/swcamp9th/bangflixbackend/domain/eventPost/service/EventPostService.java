package com.swcamp9th.bangflixbackend.domain.eventPost.service;

import com.swcamp9th.bangflixbackend.domain.eventPost.dto.EventListDTO;
import com.swcamp9th.bangflixbackend.domain.eventPost.dto.EventPostCreateDTO;
import com.swcamp9th.bangflixbackend.domain.eventPost.dto.EventPostDTO;
import com.swcamp9th.bangflixbackend.domain.eventPost.dto.EventPostUpdateDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EventPostService {

    void createEventPost(String loginId, EventPostCreateDTO newEvent, List<MultipartFile> images) throws IOException;

    void updateEventPost(String loginId, int eventPostCode,
                         EventPostUpdateDTO modifiedEvent, List<MultipartFile> images);

    void deleteEventPost(String loginId, int eventPostCode);

    List<EventListDTO> getEventList();

    EventPostDTO findEventByCode(int eventPostCode);
}
