package com.varc.bangflex.domain.theme.service;

import com.varc.bangflex.domain.theme.dto.FindThemeByReactionDTO;
import com.varc.bangflex.domain.theme.dto.ThemeReactionDTO;
import com.varc.bangflex.domain.theme.dto.GenreDTO;
import com.varc.bangflex.domain.theme.dto.ThemeDTO;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ThemeService {

    ThemeDTO findTheme(Integer themeCode, String loginId);

    List<GenreDTO> findGenres();

    List<ThemeDTO> findThemeByGenresAndSearchOrderBySort(Pageable pageable, String filter, List<String> genres, String content, String loginId);

    List<ThemeDTO> findThemeByStoreOrderBySort(Pageable pageable, String filter, Integer storeCode, String loginId);

    void createThemeReaction(String userId, ThemeReactionDTO themeReactionDTO);

    void deleteThemeReaction(String loginId, ThemeReactionDTO themeReactionDTO);

    List<FindThemeByReactionDTO> findThemeByMemberReaction(Pageable pageable, String loginId, String reaction);

    List<ThemeDTO> findThemeByWeek(String loginId);

    List<ThemeDTO> recommendTheme(List<Integer> themeCodes);

    List<ThemeDTO> getScrapedTheme(String loginId);
}
