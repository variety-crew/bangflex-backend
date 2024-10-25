package com.varc.bangflex.domain.eventPost.service;

import com.varc.bangflex.domain.eventPost.dto.EventListDTO;
import com.varc.bangflex.domain.eventPost.dto.EventPostCreateDTO;
import com.varc.bangflex.domain.eventPost.dto.EventPostDTO;
import com.varc.bangflex.domain.eventPost.dto.EventPostUpdateDTO;
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
