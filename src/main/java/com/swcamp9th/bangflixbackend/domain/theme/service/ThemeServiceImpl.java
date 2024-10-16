package com.swcamp9th.bangflixbackend.domain.theme.service;

import com.swcamp9th.bangflixbackend.domain.theme.dto.ThemeDTO;
import com.swcamp9th.bangflixbackend.domain.theme.entity.Theme;
import com.swcamp9th.bangflixbackend.domain.theme.repository.ThemeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ThemeServiceImpl(ThemeRepository themeRepository
                          , ModelMapper modelMapper) {
        this.themeRepository = themeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ThemeDTO findTheme(Integer themeCode) {
        Theme theme = themeRepository.findById(themeCode).orElseThrow();
        ThemeDTO themeDto = modelMapper.map(theme, ThemeDTO.class);
        themeDto.setStoreCode(theme.getStore().getStoreCode());
        return themeDto;
    }
}
