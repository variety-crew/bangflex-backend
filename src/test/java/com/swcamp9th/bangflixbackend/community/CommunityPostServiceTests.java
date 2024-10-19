package com.swcamp9th.bangflixbackend.community;

import com.swcamp9th.bangflixbackend.domain.communityPost.repository.CommunityPostRepository;
import com.swcamp9th.bangflixbackend.domain.communityPost.service.CommunityPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommunityPostServiceTests {

    @Autowired
    private CommunityPostService communityPostService;

    @Autowired
    private CommunityPostRepository communityPostRepository;

//    @DisplayName("커뮤니티 게시글 등록 테스트")
//    @Test
//    public void testCreateCommunityPost() throws IOException {
//        CommunityPostCreateDTO newPost = new CommunityPostCreateDTO("안녕하세요", "반갑습니다.");
//        communityPostService.createPost(loginId, newPost, null);
//        assertNotNull(communityPostRepository);
//    }
//
//    @DisplayName("커뮤니티 게시글 상세조회 테스트")
//    @ParameterizedTest
//    @ValueSource(ints = 1)
//    public void testFindCommunityPostByCode(int communityPostCode) throws IOException {
//        CommunityPostDTO selectedPost = communityPostService.findPostByCode(communityPostCode);
//        assertEquals(selectedPost.getCreatedAt(),
//                        communityPostRepository.findById(communityPostCode).get().getCreatedAt());
//    }
}
