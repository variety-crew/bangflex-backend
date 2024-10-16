package com.swcamp9th.bangflixbackend.domain.community.communityPost.controller;

import com.swcamp9th.bangflixbackend.common.ResponseMessage;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.*;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.service.CommunityPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController("communityPostController")
@Slf4j
@RequestMapping("api/v1/community")
public class CommunityPostController {

    private final CommunityPostService communityPostService;

    @Autowired
    public CommunityPostController(CommunityPostService communityPostService) {
        this.communityPostService = communityPostService;
    }

    /* 게시글 등록 */
    @PostMapping("/post")
    public ResponseEntity<ResponseMessage<CommunityPostDTO>> createCommunityPost(
                @RequestPart CommunityPostCreateDTO newPost,
                @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {

        CommunityPostDTO postResponse = communityPostService.createPost(newPost, images);
        return ResponseEntity.ok(new ResponseMessage<>(200, "게시글 등록 성공", postResponse));
    }

    /* 게시글 수정 */
    @PutMapping("/post/{communityPostCode}")
    public ResponseEntity<ResponseMessage<Object>> updateCommunityPost(
                @PathVariable Integer communityPostCode,
                @RequestPart CommunityPostUpdateDTO modifiedPost,
                @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {

        communityPostService.updatePost(communityPostCode, modifiedPost, images);
        return ResponseEntity.ok(new ResponseMessage<>(200, "게시글 수정 성공", null));
    }

    /* 게시글 삭제 */
    @DeleteMapping("/post/{communityPostCode}")
    public ResponseEntity<ResponseMessage<Object>> deleteCommunityPost(
                            @PathVariable Integer communityPostCode,
                            @RequestBody CommunityPostDeleteDTO deletedPost) {

        communityPostService.deletePost(communityPostCode, deletedPost);
        return ResponseEntity.ok(new ResponseMessage<>(200, "게시글 삭제 성공", null));
    }

    /* 게시글 목록 조회(페이지네이션) */
    @GetMapping("")
    public ResponseEntity<ResponseMessage<Page<CommunityPostDTO>>> findPostList(
                            @PageableDefault(size = 10) Pageable pageable) {

        Page<CommunityPostDTO> postList = communityPostService.findPostList(pageable);
        if (postList.hasContent()) {
            return ResponseEntity.ok(new ResponseMessage<>(200, "게시글 목록 조회 성공", postList));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    /* 게시글 상세 조회 */
    @PostMapping("/post/{communityPostCode}")
    public ResponseEntity<ResponseMessage<CommunityPostDTO>> findPost(@PathVariable Integer communityPostCode) {

        CommunityPostDTO post = communityPostService.findPostByCode(communityPostCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "게시글 조회 성공", post));
    }

    /* 게시글 구독 */
    @GetMapping("/post/subscribe/{communityPostCode}")
    public ResponseEntity<ResponseMessage<String>> subscribe(
            @PathVariable Integer communityPostCode,
            @RequestAttribute("loginId") String loginId
    ) {
        log.info("Community post code is {}", communityPostCode);
        log.info("loginId is {}", loginId);
        return ResponseEntity.ok(
                new ResponseMessage<>(200, "구독 완료", "helloworld")
        );
    }
}
