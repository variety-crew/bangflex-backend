package com.swcamp9th.bangflixbackend.domain.community.communityPost.controller;

import com.swcamp9th.bangflixbackend.common.ResponseMessage;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.service.SubscribeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/subscribe")
public class SubscribeController {
    private final SubscribeService subscribeService;

    @Autowired
    public SubscribeController(SubscribeService subscribeService) {
        this.subscribeService = subscribeService;
    }

    /* 게시글 구독 엔드포인트*/
    @PostMapping("post/{postId}")
    public ResponseEntity<ResponseMessage<Void>> subscribeToPost(
            @PathVariable Integer postId, @RequestAttribute String loginId
    ) {
        subscribeService.subscribe(loginId, postId);
        return ResponseEntity.ok(new ResponseMessage<>(200, "구독 완료", null));
    }

    /* 사용자별 구독 게시글 리스트 조회 엔드포인트*/
    @GetMapping("/info")
    public ResponseEntity<ResponseMessage<Map<String, Map<Integer, SseEmitter>>>> getAllSubscribes() {
        Map<String, Map<Integer, SseEmitter>> subscription = subscribeService.getAllSubscribesByMember();
        return ResponseEntity.ok(new ResponseMessage<>(200, "조회 완료", subscription));
    }

}
