package com.swcamp9th.bangflixbackend.domain.theme.service;

import com.swcamp9th.bangflixbackend.domain.theme.dto.GenreDTO;
import com.swcamp9th.bangflixbackend.domain.theme.dto.ThemeDTO;
import java.util.List;

public interface ThemeService {

    ThemeDTO findTheme(Integer themeCode);

    List<GenreDTO> findGenres();
}
