package com.swcamp9th.bangflixbackend.domain.theme.service;

import com.swcamp9th.bangflixbackend.domain.theme.dto.CreateThemeReactionDTO;
import com.swcamp9th.bangflixbackend.domain.theme.dto.GenreDTO;
import com.swcamp9th.bangflixbackend.domain.theme.dto.ThemeDTO;
import com.swcamp9th.bangflixbackend.domain.theme.entity.Genre;
import com.swcamp9th.bangflixbackend.domain.theme.entity.Theme;
import com.swcamp9th.bangflixbackend.domain.theme.entity.ThemeReaction;
import com.swcamp9th.bangflixbackend.domain.theme.entity.ThemeReaction.ReactionType;
import com.swcamp9th.bangflixbackend.domain.theme.entity.ThemeReactionId;
import com.swcamp9th.bangflixbackend.domain.theme.repository.GenreRepository;
import com.swcamp9th.bangflixbackend.domain.theme.repository.ThemeReactionRepository;
import com.swcamp9th.bangflixbackend.domain.theme.repository.ThemeRepository;
import com.swcamp9th.bangflixbackend.domain.user.entity.Member;
import com.swcamp9th.bangflixbackend.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;
    private final ModelMapper modelMapper;
    private final GenreRepository genreRepository;
    private final ThemeReactionRepository themeReactionRepository;
    private final UserRepository userRepository;

    @Autowired
    public ThemeServiceImpl(ThemeRepository themeRepository
                          , ModelMapper modelMapper
                          , GenreRepository genreRepository
                          , ThemeReactionRepository themeReactionRepository
                          , UserRepository userRepository) {
        this.themeRepository = themeRepository;
        this.modelMapper = modelMapper;
        this.genreRepository = genreRepository;
        this.themeReactionRepository = themeReactionRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ThemeDTO findTheme(Integer themeCode) {
        Theme theme = themeRepository.findById(themeCode).orElseThrow();
        ThemeDTO themeDto = createThemeDTO(theme);
        return themeDto;
    }

    @Override
    @Transactional
    public List<GenreDTO> findGenres() {
        List<Genre> genres = genreRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        return genres.stream().map(genre -> {
            return modelMapper.map(genre, GenreDTO.class);
        }).toList();
    }

    @Override
    @Transactional
    public List<ThemeDTO> findThemeByGenresAndSearchOrderBySort(Pageable pageable, String filter,
        List<String> genres, String content) {

        List<Theme> themes = new ArrayList<>();

        if(!genres.isEmpty()){
            if(content != null)
                themes = themeRepository.findThemesByAllGenresAndSearch(genres, content, genres.size());
            else
                themes = themeRepository.findThemesByAllGenres(genres, genres.size());
        } else {
            if(content != null)
                themes = themeRepository.findThemesBySearch(content);
            else
                themes = themeRepository.findAll();
        }

        List<ThemeDTO> themesDTO = new ArrayList<>();

        for(Theme theme : themes)
            themesDTO.add(createThemeDTO(theme));

        if (filter != null) {
            switch (filter) {
                case "like":
                    themesDTO.sort(Comparator.comparing(ThemeDTO::getLikeCount).reversed()
                        .thenComparing(Comparator.comparing(ThemeDTO::getCreatedAt).reversed()));
                    break;

                case "scrap":
                    themesDTO.sort(Comparator.comparing(ThemeDTO::getScrapCount).reversed()
                        .thenComparing(Comparator.comparing(ThemeDTO::getCreatedAt).reversed()));
                    break;

                case "review":
                    themesDTO.sort(Comparator.comparing(ThemeDTO::getReviewCount).reversed()
                        .thenComparing(Comparator.comparing(ThemeDTO::getCreatedAt).reversed()));
                    break;

                default:
                    themesDTO.sort(Comparator.comparing(ThemeDTO::getCreatedAt).reversed()
                        .thenComparing(Comparator.comparing(ThemeDTO::getCreatedAt).reversed()));
                    break;
            }
        } else
            themesDTO.sort(Comparator.comparing(ThemeDTO::getCreatedAt).reversed());


        int startIndex = pageable.getPageNumber() * pageable.getPageSize();
        int lastIndex = Math.min((startIndex + pageable.getPageSize()), themes.size());
        return themesDTO.subList(startIndex, lastIndex);
    }

    @Override
    @Transactional
    public List<ThemeDTO> findThemeByStoreOrderBySort(Pageable pageable, String filter,
        Integer storeCode) {
        List<Theme> themes = themeRepository.findByStoreCode(storeCode);

        List<ThemeDTO> themesDTO = new ArrayList<>();

        for(Theme theme : themes)
            themesDTO.add(createThemeDTO(theme));

        if (filter != null) {
            switch (filter) {
                case "like":
                    themesDTO.sort(Comparator.comparing(ThemeDTO::getLikeCount).reversed()
                        .thenComparing(Comparator.comparing(ThemeDTO::getCreatedAt).reversed()));
                    break;

                case "scrap":
                    themesDTO.sort(Comparator.comparing(ThemeDTO::getScrapCount).reversed()
                        .thenComparing(Comparator.comparing(ThemeDTO::getCreatedAt).reversed()));
                    break;

                case "review":
                    themesDTO.sort(Comparator.comparing(ThemeDTO::getReviewCount).reversed()
                        .thenComparing(Comparator.comparing(ThemeDTO::getCreatedAt).reversed()));
                    break;

                default:
                    themesDTO.sort(Comparator.comparing(ThemeDTO::getCreatedAt).reversed()
                        .thenComparing(Comparator.comparing(ThemeDTO::getCreatedAt).reversed()));
                    break;
            }
        } else
            themesDTO.sort(Comparator.comparing(ThemeDTO::getCreatedAt).reversed());


        int startIndex = pageable.getPageNumber() * pageable.getPageSize();
        int lastIndex = Math.min((startIndex + pageable.getPageSize()), themes.size());
        return themesDTO.subList(startIndex, lastIndex);
    }

    @Override
    public void createThemeReaction(String userId, CreateThemeReactionDTO createThemeReactionDTO) {
        Member member = userRepository.findById(userId).orElseThrow();
        Theme theme = themeRepository.findById(createThemeReactionDTO.getThemeCode()).orElseThrow();
        ThemeReaction themeReaction = themeReactionRepository.findByIds(
            createThemeReactionDTO.getThemeCode(), member.getMemberCode());

        if(themeReaction == null){
            themeReaction.setMember(member);
            if(createThemeReactionDTO.getReaction().equals("like"))
                themeReaction.setReaction(ReactionType.LIKE);
            else
                themeReaction.setReaction(ReactionType.SCRAP);
            themeReaction.setCreatedAt(LocalDateTime.now());
            themeReaction.setActive(true);
            themeReaction.setTheme(theme);
        }
        else {
            if(createThemeReactionDTO.getReaction().equals("like")){
                if(themeReaction.getReaction().equals(ReactionType.LIKE))
                    return;
                else if (themeReaction.getReaction().equals(ReactionType.SCRAP))
                    themeReaction.setReaction(ReactionType.SCRAP_AND_LIKE);
                else if (themeReaction.getReaction().equals(ReactionType.SCRAP_AND_LIKE))
                    return;
            }
            else{
                if(themeReaction.getReaction().equals(ReactionType.LIKE))
                    themeReaction.setReaction(ReactionType.SCRAP_AND_LIKE);
                else if (themeReaction.getReaction().equals(ReactionType.SCRAP))
                    return;
                else if (themeReaction.getReaction().equals(ReactionType.SCRAP_AND_LIKE))
                    return;
            }
        }

        themeReactionRepository.save(themeReaction);
    }

    private ThemeDTO createThemeDTO(Theme theme) {
        ThemeDTO themeDto = modelMapper.map(theme, ThemeDTO.class);
        themeDto.setStoreCode(theme.getStore().getStoreCode());
        themeDto.setLikeCount(themeRepository.countLikesByThemeCode(theme.getThemeCode()));
        themeDto.setScrapCount(themeRepository.countScrapsByThemeCode(theme.getThemeCode()));
        themeDto.setReviewCount(themeRepository.countReviewsByThemeCode(theme.getThemeCode()));

        return themeDto;
    }
}
