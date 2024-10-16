package com.swcamp9th.bangflixbackend.domain.theme.service;

import com.swcamp9th.bangflixbackend.domain.theme.dto.GenreDTO;
import com.swcamp9th.bangflixbackend.domain.theme.dto.ThemeDTO;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ThemeService {

    ThemeDTO findTheme(Integer themeCode);

    List<GenreDTO> findGenres();

    List<ThemeDTO> findThemeByGenresAndSearchOrderBySort(Pageable pageable, String filter, List<String> genres, String content);

    List<ThemeDTO> findThemeByStoreOrderBySort(Pageable pageable, String filter, Integer storeCode);
}
