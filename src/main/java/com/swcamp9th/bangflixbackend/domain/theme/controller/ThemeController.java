package com.swcamp9th.bangflixbackend.domain.theme.controller;

import com.swcamp9th.bangflixbackend.common.ResponseMessage;
import com.swcamp9th.bangflixbackend.domain.store.dto.StoreDTO;
import com.swcamp9th.bangflixbackend.domain.theme.dto.GenreDTO;
import com.swcamp9th.bangflixbackend.domain.theme.dto.ThemeDTO;
import com.swcamp9th.bangflixbackend.domain.theme.service.ThemeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/themes")
public class ThemeController {

    private final ThemeService themeService;

    @Autowired
    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping("/{themeCode}")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ResponseMessage<ThemeDTO>> findTheme(
        @PathVariable("themeCode") Integer themeCode) {

        ThemeDTO theme = themeService.findTheme(themeCode);

        return ResponseEntity.ok(new ResponseMessage<>(200, themeCode + "번 테마 조회 성공", theme));
    }

    @GetMapping("/genres")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ResponseMessage<List<GenreDTO>>> findGenres() {

        List<GenreDTO> genres = themeService.findGenres();

        return ResponseEntity.ok(new ResponseMessage<>(200, "전체 장르 조회 성공", genres));
    }

    @GetMapping("")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ResponseMessage<List<ThemeDTO>>> findThemeByGenresAndSearchOrderBySort(
        @PageableDefault(size = 10) Pageable pageable,
        @RequestParam(required = false) String filter,
        @RequestParam(required = false) List<String> genres,
        @RequestParam(required = false) String content
    ) {

        List<ThemeDTO> themes = themeService.findThemeByGenresAndSearchOrderBySort(pageable, filter, genres, content);

        return ResponseEntity.ok(new ResponseMessage<>(200, "테마 조회 성공", themes));
    }

    @GetMapping("/stores/{storeCode}")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<ResponseMessage<List<ThemeDTO>>> findThemeByStoreOrderBySort(
        @PathVariable("storeCode") Integer storeCode,
        @PageableDefault(size = 10) Pageable pageable,
        @RequestParam(required = false) String filter
    ) {

        List<ThemeDTO> themes = themeService.findThemeByStoreOrderBySort(pageable, filter, storeCode);

        return ResponseEntity.ok(new ResponseMessage<>(200, "테마 조회 성공", themes));
    }

}
