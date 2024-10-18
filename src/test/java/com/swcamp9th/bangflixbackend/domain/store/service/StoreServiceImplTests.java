package com.swcamp9th.bangflixbackend.domain.store.service;

import static org.junit.jupiter.api.Assertions.*;

import com.swcamp9th.bangflixbackend.domain.review.dto.CreateReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.dto.ReviewDTO;
import com.swcamp9th.bangflixbackend.domain.review.entity.Review;
import com.swcamp9th.bangflixbackend.domain.review.entity.ReviewLike;
import com.swcamp9th.bangflixbackend.domain.review.enums.Activity;
import com.swcamp9th.bangflixbackend.domain.review.enums.Composition;
import com.swcamp9th.bangflixbackend.domain.review.enums.HorrorLevel;
import com.swcamp9th.bangflixbackend.domain.review.enums.Interior;
import com.swcamp9th.bangflixbackend.domain.review.enums.Level;
import com.swcamp9th.bangflixbackend.domain.review.enums.Probability;
import com.swcamp9th.bangflixbackend.domain.review.repository.ReviewLikeRepository;
import com.swcamp9th.bangflixbackend.domain.review.repository.ReviewRepository;
import com.swcamp9th.bangflixbackend.domain.store.dto.StoreDTO;
import com.swcamp9th.bangflixbackend.domain.store.entity.Store;
import com.swcamp9th.bangflixbackend.domain.store.repository.StoreRepository;
import com.swcamp9th.bangflixbackend.domain.theme.entity.Theme;
import com.swcamp9th.bangflixbackend.domain.theme.repository.ThemeRepository;
import com.swcamp9th.bangflixbackend.domain.user.entity.Member;
import com.swcamp9th.bangflixbackend.domain.user.repository.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
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
class StoreServiceImplTests {

    private final StoreService storeService;

    @Autowired
    public StoreServiceImplTests(StoreService storeService) {
        this.storeService = storeService;
    }

    @MockBean
    private StoreRepository storeRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    @Transactional
    public void testFindStore() {
        // given
        Integer storeCode = 1000;

        Store mockStore = Store.builder()
            .storeCode(1000)
            .name("test용 store 이름")
            .address("test용 store 주소")
            .active(true)
            .createdAt(LocalDateTime.now())
            .pageUrl("test용 store 웹페이지 url")
            .build();

        StoreDTO mockStoreDTO = new StoreDTO();
        mockStoreDTO.setStoreCode(1000);
        mockStoreDTO.setName("test용 store 이름");
        mockStoreDTO.setAddress("test용 store 주소");
        mockStoreDTO.setPageUrl("test용 store 웹페이지 url");

        // StoreRepository가 호출될 때 Optional로 감싼 mockStore를 반환하도록 설정
        Mockito.when(storeRepository.findById(storeCode)).thenReturn(Optional.of(mockStore));
        // ModelMapper가 호출될 때 mockStore를 mockStoreDTO로 매핑하여 반환하도록 설정
        Mockito.when(modelMapper.map(mockStore, StoreDTO.class)).thenReturn(mockStoreDTO);

        // when
        StoreDTO foundStore = storeService.findStore(storeCode);

        // then
        assertNotNull(foundStore); // 반환된 StoreDTO가 null이 아님을 검증
        assertEquals(storeCode, foundStore.getStoreCode());
        Mockito.verify(storeRepository, Mockito.times(1)).findById(storeCode); // findById가 한 번 호출되었는지 검증
        Mockito.verify(modelMapper, Mockito.times(1)).map(mockStore, StoreDTO.class); // map 메서드가 한 번 호출되었는지 검증
    }

}