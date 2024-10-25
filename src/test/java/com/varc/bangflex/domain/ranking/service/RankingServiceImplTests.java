package com.varc.bangflex.domain.ranking.service;

import com.varc.bangflex.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

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

//    @DisplayName("랭킹 순 유저 반환 테스트")
//    @Test
//    @Transactional
//    public void testFindAllMemberRanking() {
//        Pageable pageable = PageRequest.of(0, 10);
//        Member mockMember = new Member();
//
//        Mockito.when(userRepository.save(Member.builder()
//            .id(String.valueOf(UUID.randomUUID()))
//            .isAdmin(false)
//            .email(String.valueOf(UUID.randomUUID()))
//            .password(passwordEncoder.encode(String.valueOf(UUID.randomUUID())))
//            .nickname(String.valueOf(UUID.randomUUID()))
//            .build())).thenReturn(mockMember);
//
//        // when
//        List<MemberRankingDTO> memberRankingDTOS =  rankingService.findAllMemberRanking(pageable);
//
//        // then
//        Mockito.verify(userRepository, Mockito.times(1)).save(mockMember);
//        assertFalse(memberRankingDTOS.isEmpty());
//    }
}