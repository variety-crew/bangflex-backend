package com.swcamp9th.bangflixbackend.domain.eventPost.controller;

import com.swcamp9th.bangflixbackend.common.ResponseMessage;
import com.swcamp9th.bangflixbackend.domain.eventPost.dto.EventPostCreateDTO;
import com.swcamp9th.bangflixbackend.domain.eventPost.service.EventPostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController("eventPostController")
@Slf4j
@RequestMapping("api/v1/event")
public class EventPostController {

    private final EventPostService eventPostService;

    @Autowired
    public EventPostController(EventPostService eventPostService) {
        this.eventPostService = eventPostService;
    }

    /* 이벤트 게시글 등록 */
    @PostMapping(value = "/post", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ResponseMessage<Object>> createEventPost(
            @RequestAttribute("loginId") String loginId,
            @Valid @RequestPart EventPostCreateDTO newEvent,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {

        eventPostService.createEvent(loginId, newEvent, images);
        return ResponseEntity.ok(new ResponseMessage<>(200, "게시글 등록 성공", null));
    }


    /* 이벤트 게시글 수정 */


    /* 이벤트 게시글 삭제 */


    /* 이벤트 게시글 목록 조회 */


    /* 이벤트 게시글 상세 조회 */
}
