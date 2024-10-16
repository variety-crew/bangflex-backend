package com.swcamp9th.bangflixbackend.domain.theme.service;

import com.swcamp9th.bangflixbackend.domain.review.dto.ReviewDTO;
import com.swcamp9th.bangflixbackend.domain.theme.dto.GenreDTO;
import com.swcamp9th.bangflixbackend.domain.theme.dto.ThemeDTO;
import com.swcamp9th.bangflixbackend.domain.theme.entity.Genre;
import com.swcamp9th.bangflixbackend.domain.theme.entity.Theme;
import com.swcamp9th.bangflixbackend.domain.theme.repository.GenreRepository;
import com.swcamp9th.bangflixbackend.domain.theme.repository.ThemeRepository;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;
    private final ModelMapper modelMapper;
    private final GenreRepository genreRepository;

    @Autowired
    public ThemeServiceImpl(ThemeRepository themeRepository
                          , ModelMapper modelMapper
                          , GenreRepository genreRepository) {
        this.themeRepository = themeRepository;
        this.modelMapper = modelMapper;
        this.genreRepository = genreRepository;
    }

    @Override
    public ThemeDTO findTheme(Integer themeCode) {
        Theme theme = themeRepository.findById(themeCode).orElseThrow();
        ThemeDTO themeDto = modelMapper.map(theme, ThemeDTO.class);
        themeDto.setStoreCode(theme.getStore().getStoreCode());
        return themeDto;
    }

    @Override
    public List<GenreDTO> findGenres() {
        List<Genre> genres = genreRepository.findAll();

        return genres.stream().map(genre -> {
            return modelMapper.map(genre, GenreDTO.class);
        }).toList();
    }
}
