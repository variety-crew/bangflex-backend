package com.swcamp9th.bangflixbackend.community;

import com.swcamp9th.bangflixbackend.domain.comment.repository.CommentRepository;
import com.swcamp9th.bangflixbackend.domain.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommentServiceTests {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

//    @DisplayName("댓글 등록 테스트")
//    @ParameterizedTest
//    @ValueSource(ints = 1)
//    public void testCreateComment(int communityPostCode) {
//        CommentCreateDTO newComment = new CommentCreateDTO("아무도 없나요..", 1);
//        commentService.createComment(communityPostCode, newComment);
//        assertNotNull(commentRepository);
//    }

//    @DisplayName("댓글 조회 테스트")
//    @ParameterizedTest
//    @ValueSource(ints = 1)
//    public void testGetAllCommentsOfPost(int communityPostCode) {
//        List<CommentDTO> comments = commentService.getAllCommentsOfPost(communityPostCode);
//        assertEquals(1, comments.size());
//    }
}
