package com.swcamp9th.bangflixbackend.community;

import com.swcamp9th.bangflixbackend.domain.community.communityPost.dto.CommunityPostDTO;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.CommunityPost;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.entity.Member;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.repository.CommunityPostRepository;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.repository.MemberRepository;
import com.swcamp9th.bangflixbackend.domain.community.communityPost.service.CommunityPostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommunityPostServiceTests {

    @Autowired
    private CommunityPostService communityPostService;

    @Autowired
    private CommunityPostRepository communityPostRepository;

    @Autowired
    private ModelMapper modelMapper;

    @DisplayName("커뮤니티 게시글 등록 테스트")
    @Test
    public void testInsertCommunityPost() throws IOException {
        CommunityPostDTO newPost = new CommunityPostDTO(
                "안녕하세요", "반갑습니다.", LocalDateTime.now(), true, 1, null);
        communityPostService.createPost(newPost, null);
        assertNotNull(communityPostRepository);
    }

    @DisplayName("커뮤니티 게시글 수정 테스트")
    @ParameterizedTest
    @ValueSource(ints = 1)
    public void testUpdateCommunityPost(int postCode) throws IOException {
        CommunityPostDTO modifiedPost = new CommunityPostDTO(
                        postCode, "안녕하세요", "날씨가 좋아요!", 1, null);
        assertDoesNotThrow(() -> communityPostService.modifyPost(1, modifiedPost, null));
    }

    @DisplayName("커뮤니티 게시글 삭제 테스트")
    @ParameterizedTest
    @ValueSource(ints = 1)
    public void testDeleteCommunityPost(int postCode) {
        CommunityPostDTO deletedPost = new CommunityPostDTO(postCode, 1);

        communityPostService.deletePost(postCode, deletedPost);
        assertFalse(communityPostRepository.findById(postCode).get().getActive());
    }
}
