package com.swcamp9th.bangflixbackend.domain.theme.service;

import com.swcamp9th.bangflixbackend.domain.theme.dto.FindThemeByReactionDTO;
import com.swcamp9th.bangflixbackend.domain.theme.dto.ThemeReactionDTO;
import com.swcamp9th.bangflixbackend.domain.theme.dto.GenreDTO;
import com.swcamp9th.bangflixbackend.domain.theme.dto.ThemeDTO;
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
}
