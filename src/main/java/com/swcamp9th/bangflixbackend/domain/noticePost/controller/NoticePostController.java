package com.swcamp9th.bangflixbackend.domain.noticePost.controller;

import com.swcamp9th.bangflixbackend.common.ResponseMessage;
import com.swcamp9th.bangflixbackend.domain.noticePost.dto.NoticePostCreateDTO;
import com.swcamp9th.bangflixbackend.domain.noticePost.dto.NoticePostUpdateDTO;
import com.swcamp9th.bangflixbackend.domain.noticePost.service.NoticePostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController("noticePostController")
@Slf4j
@RequestMapping("api/v1/notice")
public class NoticePostController {

    private final NoticePostService noticePostService;

    @Autowired
    public NoticePostController(NoticePostService noticePostService) {
        this.noticePostService = noticePostService;
    }

    /* 공지사항 게시글 등록 */
    @PostMapping(value = "/post", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ResponseMessage<Object>> createNoticePost(
            @Valid @RequestPart NoticePostCreateDTO newNotice,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId;

        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            userId = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
        } else {
            // principal이 UserDetails가 아닌 경우 (ex: 익명 사용자)
            return ResponseEntity.ok(new ResponseMessage<>(401, "인증되지 않은 사용자입니다.", null));
        }

        noticePostService.createNoticePost(newNotice, images, userId);
        return ResponseEntity.ok(new ResponseMessage<>(200, "게시글 등록 성공", null));
    }

    /* 공지사항 게시글 수정 */
    @PutMapping(value = "/post/{noticePostCode}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ResponseMessage<Object>> updateNoticePost(
            @PathVariable int noticePostCode,
            @Valid @RequestPart NoticePostUpdateDTO updatedNotice,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId;

        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            userId = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
        } else {
            // principal이 UserDetails가 아닌 경우 (ex: 익명 사용자)
            return ResponseEntity.ok(new ResponseMessage<>(401, "인증되지 않은 사용자입니다.", null));
        }

        noticePostService.updateNoticePost(noticePostCode, updatedNotice, images, userId);
        return ResponseEntity.ok(new ResponseMessage<>(200, "게시글 수정 성공", null));
    }

    /* 공지사항 게시글 삭제 */
    @DeleteMapping("/post/{noticePostCode}")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ResponseMessage<Object>> deleteNoticePost(@PathVariable int noticePostCode) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId;

        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            userId = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
        } else {
            // principal이 UserDetails가 아닌 경우 (ex: 익명 사용자)
            return ResponseEntity.ok(new ResponseMessage<>(401, "인증되지 않은 사용자입니다.", null));
        }

        noticePostService.deleteNoticePost(noticePostCode, userId);
        return ResponseEntity.ok(new ResponseMessage<>(200, "게시글 삭제 성공", null));
    }
}
