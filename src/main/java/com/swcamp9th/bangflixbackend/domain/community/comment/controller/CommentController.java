package com.swcamp9th.bangflixbackend.domain.community.comment.controller;

import com.swcamp9th.bangflixbackend.common.ResponseMessage;
import com.swcamp9th.bangflixbackend.domain.community.comment.dto.CommentDTO;
import com.swcamp9th.bangflixbackend.domain.community.comment.dto.CommentCreateDTO;
import com.swcamp9th.bangflixbackend.domain.community.comment.dto.CommentDeleteDTO;
import com.swcamp9th.bangflixbackend.domain.community.comment.dto.CommentUpdateDTO;
import com.swcamp9th.bangflixbackend.domain.community.comment.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("commentController")
@Slf4j
@RequestMapping("api/v1/community/post/{communityPostCode}/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /* 댓글 등록 */
    @PostMapping("")
    public ResponseEntity<ResponseMessage<CommentDTO>> createComment(
                                            @PathVariable("communityPostCode") Integer communityPostCode,
                                            @RequestBody CommentCreateDTO newComment) {

        CommentDTO createdComment = commentService.createComment(communityPostCode, newComment);
        return ResponseEntity.ok(new ResponseMessage<>(200, "댓글 등록 성공", createdComment));
    }

    /* 댓글 수정 */
    @PutMapping("/{commentCode}")
    public ResponseEntity<ResponseMessage<CommentDTO>> updateComment(
                                            @PathVariable("communityPostCode") Integer communityPostCode,
                                            @PathVariable("commentCode") Integer commentCode,
                                            @RequestBody CommentUpdateDTO modifiedComment) {

        CommentDTO updatedComment = commentService.updateComment(communityPostCode, commentCode, modifiedComment);
        return ResponseEntity.ok(new ResponseMessage<>(200, "댓글 수정 성공", updatedComment));
    }

    /* 댓글 삭제 */
    @DeleteMapping("/{commentCode}")
    public ResponseEntity<ResponseMessage<Object>> deleteComment(
                                            @PathVariable("communityPostCode") Integer communityPostCode,
                                            @PathVariable("commentCode") Integer commentCode,
                                            @RequestBody CommentDeleteDTO deletedComment) {

        commentService.deleteComment(communityPostCode, commentCode, deletedComment);
        return ResponseEntity.ok(new ResponseMessage<>(200, "댓글 삭제 성공", null));
    }

    /* 게시글의 댓글 목록 조회 */
    @GetMapping("")
    public ResponseEntity<ResponseMessage<List<CommentDTO>>> getAllComments(
                                            @PathVariable("communityPostCode") Integer communityPostCode) {

        List<CommentDTO> commentsOfPost = commentService.getAllCommentsOfPost(communityPostCode);
        return ResponseEntity.ok(new ResponseMessage<>(200, "댓글 조회 성공", commentsOfPost));
    }
}
