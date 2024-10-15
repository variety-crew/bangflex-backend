package com.swcamp9th.bangflixbackend.community;

import com.swcamp9th.bangflixbackend.domain.community.comment.dto.CommentCreateDTO;
import com.swcamp9th.bangflixbackend.domain.community.comment.repository.CommentRepository;
import com.swcamp9th.bangflixbackend.domain.community.comment.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommentServiceTests {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("댓글 등록 테스트")
    @ParameterizedTest
    @ValueSource(ints = 1)
    public void testCreateComment(int communityPostCode) {
        CommentCreateDTO newComment = new CommentCreateDTO("아무도 없나요..", 1);
        commentService.createComment(communityPostCode, newComment);
        assertNotNull(commentRepository);
    }
}
