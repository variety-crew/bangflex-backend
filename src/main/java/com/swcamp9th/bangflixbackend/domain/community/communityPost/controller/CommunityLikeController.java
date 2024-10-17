package com.swcamp9th.bangflixbackend.domain.community.communityPost.controller;

import com.swcamp9th.bangflixbackend.common.ResponseMessage;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityLikeCreateDTO;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.service.CommunityLikeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    /* 좋아요 등록 및 취소 */
    @PostMapping("")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ResponseMessage<Object>> addLike(@RequestAttribute("loginId") String loginId,
                                                                     @RequestBody CommunityLikeCreateDTO newLike) {

        communityLikeService.addLike(loginId, newLike);
        return ResponseEntity.ok(new ResponseMessage<>(200, "좋아요 또는 좋아요 취소 성공", null));
    }
}
