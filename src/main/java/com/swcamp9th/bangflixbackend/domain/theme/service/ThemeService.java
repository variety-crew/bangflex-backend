package com.swcamp9th.bangflixbackend.domain.theme.service;

import com.swcamp9th.bangflixbackend.domain.theme.dto.ThemeDTO;

public interface ThemeService {

    ThemeDTO findTheme(Integer themeCode);
}
