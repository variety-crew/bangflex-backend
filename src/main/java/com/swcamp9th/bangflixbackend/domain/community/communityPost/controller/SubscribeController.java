package com.swcamp9th.bangflixbackend.domain.community.communityPost.controller;

import com.swcamp9th.bangflixbackend.common.ResponseMessage;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.service.SubscribeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("/api/v1/subscribe/post/")
public class SubscribeController {
    private final SubscribeService subscribeService;

    @Autowired
    public SubscribeController(SubscribeService subscribeService) {
        this.subscribeService = subscribeService;
    }


    /* 게시글 구독 엔드포인트*/
    @PostMapping("/{postId}")
    public ResponseEntity<ResponseMessage<Void>> subscribeToPost(
            @PathVariable Long postId, @RequestAttribute String loginId
    ) {
        SseEmitter emitter = subscribeService.subscribe(loginId, postId);
        return ResponseEntity.ok(new ResponseMessage<>(200, "구독 완료", null));
    }

}
