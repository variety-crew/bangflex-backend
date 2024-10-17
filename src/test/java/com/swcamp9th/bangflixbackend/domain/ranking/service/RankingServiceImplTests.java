package com.swcamp9th.bangflixbackend.domain.ranking.service;

import static org.junit.jupiter.api.Assertions.*;

import com.swcamp9th.bangflixbackend.domain.ranking.dto.MemberRankingDTO;
import com.swcamp9th.bangflixbackend.domain.review.entity.Review;
import com.swcamp9th.bangflixbackend.domain.user.dto.SignupRequestDto;
import com.swcamp9th.bangflixbackend.domain.user.entity.Member;
import com.swcamp9th.bangflixbackend.domain.user.repository.UserRepository;
import com.swcamp9th.bangflixbackend.domain.user.service.UserService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class RankingServiceImplTests {


    private final RankingService rankingService;
    private final PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;


    @Autowired
    public RankingServiceImplTests(RankingService rankingService, PasswordEncoder passwordEncoder) {
        this.rankingService = rankingService;
        this.passwordEncoder = passwordEncoder;
    }

    @DisplayName("랭킹 순 유저 반환 테스트")
    @Test
    @Transactional
    public void testFindAllMemberRanking() {
        Pageable pageable = PageRequest.of(0, 10);
        Member mockMember = new Member();

        Mockito.when(userRepository.save(Member.builder()
            .id(String.valueOf(UUID.randomUUID()))
            .isAdmin(false)
            .email(String.valueOf(UUID.randomUUID()))
            .password(passwordEncoder.encode(String.valueOf(UUID.randomUUID())))
            .nickname(String.valueOf(UUID.randomUUID()))
            .build())).thenReturn(mockMember);

        // when
        List<MemberRankingDTO> memberRankingDTOS =  rankingService.findAllMemberRanking(pageable);

        // then
        Mockito.verify(userRepository, Mockito.times(1)).save(mockMember);
        assertFalse(memberRankingDTOS.isEmpty());
    }
}