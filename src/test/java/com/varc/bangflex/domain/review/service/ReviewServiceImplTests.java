package com.varc.bangflex.domain.review.service;

import static org.junit.jupiter.api.Assertions.*;

import com.varc.bangflex.domain.review.dto.CreateReviewDTO;
import com.varc.bangflex.domain.review.entity.Review;
import com.varc.bangflex.domain.review.enums.Activity;
import com.varc.bangflex.domain.review.enums.Composition;
import com.varc.bangflex.domain.review.enums.HorrorLevel;
import com.varc.bangflex.domain.review.enums.Interior;
import com.varc.bangflex.domain.review.enums.Level;
import com.varc.bangflex.domain.review.enums.Probability;
import com.varc.bangflex.domain.review.repository.ReviewRepository;
import com.varc.bangflex.domain.theme.entity.Theme;
import com.varc.bangflex.domain.theme.repository.ThemeRepository;
import com.varc.bangflex.domain.user.entity.Member;
import com.varc.bangflex.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
public class ReviewServiceImplTests {

    private final ReviewService reviewService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ReviewServiceImplTests(ReviewService reviewService, PasswordEncoder passwordEncoder) {
        this.reviewService = reviewService;
        this.passwordEncoder = passwordEncoder;
    }

    @MockBean
    private ReviewRepository reviewRepository;

    @MockBean
    private ThemeRepository themeRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    @Transactional
    public void testCreateReview() throws IOException, URISyntaxException {
        // given
        CreateReviewDTO newReviewDTO = new CreateReviewDTO();
        newReviewDTO.setThemeCode(1000);
        newReviewDTO.setActivity(Activity.ONE);
        newReviewDTO.setComposition(Composition.ONE);
        newReviewDTO.setContent("test");
        newReviewDTO.setInterior(Interior.ONE);
        newReviewDTO.setLevel(Level.ONE);
        newReviewDTO.setHeadcount(1);
        newReviewDTO.setProbability(Probability.FIVE);
        newReviewDTO.setHorrorLevel(HorrorLevel.ONE);
        newReviewDTO.setTakenTime(60);
        newReviewDTO.setTotalScore(6);

        String id = UUID.randomUUID().toString();
        Review mockReview = new Review();
        Theme mockTheme = new Theme();
        Member mockMember = Member.builder()
            .id(id)
            .isAdmin(false)
            .email(String.valueOf(UUID.randomUUID()))
            .password(passwordEncoder.encode(String.valueOf(UUID.randomUUID())))
            .nickname(String.valueOf(UUID.randomUUID()))
            .build();


        List<MultipartFile> images = null;

        Mockito.when(modelMapper.map(newReviewDTO, Review.class)).thenReturn(mockReview);
        Mockito.when(themeRepository.findById(1000)).thenReturn(Optional.of(mockTheme));
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(mockMember));
        Mockito.when(reviewRepository.save(mockReview)).thenReturn(mockReview);

        // when
        reviewService.createReview(newReviewDTO, images, id);

        // then
        Mockito.verify(reviewRepository, Mockito.times(1)).save(mockReview);
        Mockito.verify(userRepository, Mockito.times(1)).save(mockMember);
        assertEquals(mockTheme, mockReview.getTheme()); // 리뷰에 테마가 설정되었는지 확인
        assertEquals(mockMember, mockReview.getMember()); // 리뷰에 멤버가 설정되었는지 확인
    }
}