package com.swcamp9th.bangflixbackend.domain.theme.service;

import com.swcamp9th.bangflixbackend.domain.theme.dto.FindThemeByReactionDTO;
import com.swcamp9th.bangflixbackend.domain.theme.dto.ThemeReactionDTO;
import com.swcamp9th.bangflixbackend.domain.theme.dto.GenreDTO;
import com.swcamp9th.bangflixbackend.domain.theme.dto.ThemeDTO;
import com.swcamp9th.bangflixbackend.domain.theme.entity.Genre;
import com.swcamp9th.bangflixbackend.domain.theme.entity.ReactionType;
import com.swcamp9th.bangflixbackend.domain.theme.entity.Theme;
import com.swcamp9th.bangflixbackend.domain.theme.entity.ThemeReaction;
import com.swcamp9th.bangflixbackend.domain.theme.repository.GenreRepository;
import com.swcamp9th.bangflixbackend.domain.theme.repository.ThemeReactionRepository;
import com.swcamp9th.bangflixbackend.domain.theme.repository.ThemeRepository;
import com.swcamp9th.bangflixbackend.domain.user.entity.Member;
import com.swcamp9th.bangflixbackend.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
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
                themes = themeRepository.findThemesByAllGenresAndSearch(genres, content);
            else
                themes = themeRepository.findThemesByAllGenres(genres);
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
    @Transactional
    public void createThemeReaction(String userId, ThemeReactionDTO themeReactionDTO) {
        Member member = userRepository.findById(userId).orElseThrow();
        Theme theme = themeRepository.findById(themeReactionDTO.getThemeCode()).orElseThrow();
        ThemeReaction themeReaction = themeReactionRepository.findByIds(
            themeReactionDTO.getThemeCode(), member.getMemberCode());

        if(themeReaction == null){
            themeReaction = new ThemeReaction();
            themeReaction.setMember(member);
            if(themeReactionDTO.getReaction().equals("like"))
                themeReaction.setReaction(ReactionType.LIKE);
            else if (themeReactionDTO.getReaction().equals("scrap"))
                themeReaction.setReaction(ReactionType.SCRAP);
            themeReaction.setCreatedAt(LocalDateTime.now());
            themeReaction.setActive(true);
            themeReaction.setTheme(theme);
            themeReaction.setThemeCode(theme.getThemeCode());
            themeReaction.setMemberCode(member.getMemberCode());
            themeReactionRepository.save(themeReaction);
        }
        else {
            if(themeReactionDTO.getReaction().equals("like")){
                if(themeReaction.getReaction().equals(ReactionType.LIKE))
                    return;
                else if (themeReaction.getReaction().equals(ReactionType.SCRAP))
                    themeReaction.setReaction(ReactionType.SCRAPLIKE);
                else if (themeReaction.getReaction().equals(ReactionType.SCRAPLIKE))
                    return;
            }
            else if (themeReactionDTO.getReaction().equals("scrap")){
                if(themeReaction.getReaction().equals(ReactionType.LIKE))
                    themeReaction.setReaction(ReactionType.SCRAPLIKE);
                else if (themeReaction.getReaction().equals(ReactionType.SCRAP))
                    return;
                else if (themeReaction.getReaction().equals(ReactionType.SCRAPLIKE))
                    return;
            }
            themeReactionRepository.save(themeReaction);
        }
    }

    @Override
    @Transactional
    public void deleteThemeReaction(String loginId, ThemeReactionDTO themeReactionDTO) {
        Member member = userRepository.findById(loginId).orElseThrow();
        ThemeReaction themeReaction = themeReactionRepository.findByIds(
            themeReactionDTO.getThemeCode(), member.getMemberCode());

        if(themeReaction != null) {
            if (themeReactionDTO.getReaction().equals("like")) {
                if (themeReaction.getReaction().equals(ReactionType.LIKE))
                    themeReactionRepository.delete(themeReaction);
                else if (themeReaction.getReaction().equals(ReactionType.SCRAP))
                    return;
                else if (themeReaction.getReaction().equals(ReactionType.SCRAPLIKE)){
                    themeReaction.setReaction(ReactionType.SCRAP);
                    themeReactionRepository.save(themeReaction);
                }
            } else if (themeReactionDTO.getReaction().equals("scrap")) {
                if (themeReaction.getReaction().equals(ReactionType.LIKE))
                    return;
                else if (themeReaction.getReaction().equals(ReactionType.SCRAP))
                    themeReactionRepository.delete(themeReaction);
                else if (themeReaction.getReaction().equals(ReactionType.SCRAPLIKE)){
                    themeReaction.setReaction(ReactionType.LIKE);
                    themeReactionRepository.save(themeReaction);
                }
            }
        }
    }

    @Override
    @Transactional
    public List<FindThemeByReactionDTO> findThemeByMemberReaction(Pageable pageable, String loginId, String reaction) {
        Member member = userRepository.findById(loginId).orElseThrow();
        List<ThemeReaction> themeReactions = new ArrayList<>();

        if(reaction.equals("like"))
            themeReactions = themeReactionRepository.findThemeByMemberLike(
                pageable, member.getMemberCode());

        else if(reaction.equals("scrap"))
            themeReactions = themeReactionRepository.findThemeByMemberScrap(
                pageable, member.getMemberCode());

        else
            throw new RuntimeException();

        log.info("테마 리액션" + themeReactions.isEmpty() + "memberCode" + member.getMemberCode());

        return themeReactions.stream().map(themeReaction -> {
            return modelMapper.map(themeReaction.getTheme(), FindThemeByReactionDTO.class);
        }).toList();
    }

    @Override
    public List<ThemeDTO> findThemeByWeek() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekAgo = now.minusWeeks(1);  // 현재로부터 1주일 이전
        Pageable pageable = PageRequest.of(0,5);

        List<Theme> themes = themeRepository.findByWeekOrderByLikes(oneWeekAgo, pageable);

        return createThemeDTOs(themes);
    }

    private ThemeDTO createThemeDTO(Theme theme) {
        ThemeDTO themeDto = modelMapper.map(theme, ThemeDTO.class);
        themeDto.setStoreCode(theme.getStore().getStoreCode());
        themeDto.setLikeCount(themeRepository.countLikesByThemeCode(theme.getThemeCode()));
        themeDto.setScrapCount(themeRepository.countScrapsByThemeCode(theme.getThemeCode()));
        themeDto.setReviewCount(themeRepository.countReviewsByThemeCode(theme.getThemeCode()));

        return themeDto;
    }

    private List<ThemeDTO> createThemeDTOs(List<Theme> themes) {

        List<ThemeDTO> themeDTOs = new ArrayList<>();

        for(Theme theme : themes) {
            ThemeDTO themeDto = modelMapper.map(theme, ThemeDTO.class);
            themeDto.setStoreCode(theme.getStore().getStoreCode());
            themeDto.setLikeCount(themeRepository.countLikesByThemeCode(theme.getThemeCode()));
            themeDto.setScrapCount(themeRepository.countScrapsByThemeCode(theme.getThemeCode()));
            themeDto.setReviewCount(themeRepository.countReviewsByThemeCode(theme.getThemeCode()));

            themeDTOs.add(themeDto);
        }

        return themeDTOs;
    }
}
