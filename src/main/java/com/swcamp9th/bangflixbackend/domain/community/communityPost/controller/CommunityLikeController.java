package com.swcamp9th.bangflixbackend.domain.community.communityPost.controller;

import com.swcamp9th.bangflixbackend.common.ResponseMessage;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityLikeDTO;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.service.CommunityLikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("communityLikeController")
@Slf4j
@RequestMapping("api/v1/community-like")
public class CommunityLikeController {

    private final CommunityLikeService communityLikeService;

    @Autowired
    public CommunityLikeController(CommunityLikeService communityLikeService) {
        this.communityLikeService = communityLikeService;
    }

    /* 좋아요 등록 */
    @PostMapping("")
    public ResponseEntity<ResponseMessage<CommunityLikeDTO>> addLike(@RequestBody CommunityLikeDTO newLike) {

        communityLikeService.addLike(newLike);
        return ResponseEntity.ok(new ResponseMessage<>(200, "좋아요 성공", null));
    }
}
