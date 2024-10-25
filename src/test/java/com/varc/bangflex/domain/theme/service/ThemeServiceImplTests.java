package com.varc.bangflex.domain.theme.service;

import static org.junit.jupiter.api.Assertions.*;

import com.varc.bangflex.domain.store.entity.Store;
import com.varc.bangflex.domain.theme.dto.ThemeDTO;
import com.varc.bangflex.domain.theme.entity.Theme;
import com.varc.bangflex.domain.theme.repository.GenreRepository;
import com.varc.bangflex.domain.theme.repository.ThemeReactionRepository;
import com.varc.bangflex.domain.theme.repository.ThemeRepository;
import com.varc.bangflex.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@Transactional
public class ThemeServiceImplTests {

    private final ThemeService themeService;

    @Autowired
    public ThemeServiceImplTests(ThemeService themeService) {
        this.themeService = themeService;
    }

    @MockBean
    private ThemeRepository themeRepository;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private ThemeReactionRepository themeReactionRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    @Transactional
    public void testFindTheme() {
        // given
        Integer themeCode = 1000;

        // Mock 객체 생성
        Theme mockTheme = Theme.builder()
            .themeCode(1000)
            .name("test용 테마 이름")
            .store(Store.builder().storeCode(200).build()) // Store도 설정
            .build();

        ThemeDTO mockThemeDTO = new ThemeDTO();
        mockThemeDTO.setThemeCode(1000);
        mockThemeDTO.setStoreCode(200);
        mockThemeDTO.setLikeCount(10);
        mockThemeDTO.setScrapCount(5);
        mockThemeDTO.setReviewCount(20);


        // ThemeRepository가 호출될 때 Optional로 감싼 mockTheme를 반환하도록 설정
        Mockito.when(themeRepository.findById(themeCode)).thenReturn(Optional.of(mockTheme));
        // ModelMapper가 호출될 때 mockTheme을 mockThemeDTO로 매핑하여 반환하도록 설정
        Mockito.when(modelMapper.map(mockTheme, ThemeDTO.class)).thenReturn(mockThemeDTO);
        // 각 count 메서드들이 호출될 때 반환값 설정
        Mockito.when(themeRepository.countLikesByThemeCode(mockTheme.getThemeCode())).thenReturn(10);
        Mockito.when(themeRepository.countScrapsByThemeCode(mockTheme.getThemeCode())).thenReturn(5);
        Mockito.when(themeRepository.countReviewsByThemeCode(mockTheme.getThemeCode())).thenReturn(20);

        // when
        ThemeDTO foundTheme = themeService.findTheme(themeCode, null);

        // then
        assertNotNull(foundTheme); // 반환된 ThemeDTO가 null이 아님을 검증
        assertEquals(themeCode, foundTheme.getThemeCode());
        assertEquals(200, foundTheme.getStoreCode());
        assertEquals(10, foundTheme.getLikeCount());
        assertEquals(5, foundTheme.getScrapCount());
        assertEquals(20, foundTheme.getReviewCount());

        // Repository 및 ModelMapper 메서드 호출 검증
        Mockito.verify(themeRepository, Mockito.times(1)).findById(themeCode);
        Mockito.verify(modelMapper, Mockito.times(1)).map(mockTheme, ThemeDTO.class);
        Mockito.verify(themeRepository, Mockito.times(1)).countLikesByThemeCode(mockTheme.getThemeCode());
        Mockito.verify(themeRepository, Mockito.times(1)).countScrapsByThemeCode(mockTheme.getThemeCode());
        Mockito.verify(themeRepository, Mockito.times(1)).countReviewsByThemeCode(mockTheme.getThemeCode());
    }
}
