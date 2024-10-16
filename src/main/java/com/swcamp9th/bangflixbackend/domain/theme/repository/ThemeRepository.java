package com.swcamp9th.bangflixbackend.domain.theme.repository;

import com.swcamp9th.bangflixbackend.domain.theme.entity.Genre;
import com.swcamp9th.bangflixbackend.domain.theme.entity.Theme;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Integer> {

    @Query("SELECT t FROM Theme t " +
        "INNER JOIN ThemeGenre tg ON t.themeCode = tg.theme.themeCode " +
        "INNER JOIN Genre g ON tg.genreCode = g.genreCode " +
        "WHERE g.name IN :genres " + // 장르 필터
        "AND (t.name LIKE %:search% OR :search IS NULL) " + // 검색 필터
        "GROUP BY t.themeCode " +
        "HAVING COUNT(DISTINCT g.name) = :genreCount") // 모든 장르가 일치하는지 확인
    List<Theme> findThemesByAllGenresAndSearch(List<String> genres, String search, int genreCount);

    // 추가적인 쿼리 메소드로 리뷰 개수, 좋아요 개수, 스크랩 개수를 구하는 메소드
    @Query("SELECT COUNT(r) FROM Review r WHERE r.theme.themeCode = :themeCode")
    Integer countReviewsByThemeCode(Integer themeCode);

    @Query("SELECT COUNT(r) FROM ThemeReaction r WHERE r.theme.themeCode = :themeCode AND (r.reaction = 'like' OR r.reaction = 'scrap&like')")
    Integer countLikesByThemeCode(Integer themeCode);

    @Query("SELECT COUNT(r) FROM ThemeReaction r WHERE r.theme.themeCode = :themeCode AND (r.reaction = 'scrap' OR r.reaction = 'scrap&like')")
    Integer countScrapsByThemeCode(Integer themeCode);

    @Query("SELECT t FROM Theme t " +
        "INNER JOIN ThemeGenre tg ON t.themeCode = tg.theme.themeCode " +
        "INNER JOIN Genre g ON tg.genreCode = g.genreCode " +
        "WHERE g.name IN :genres " + // 장르 필터
        "GROUP BY t.themeCode " +
        "HAVING COUNT(DISTINCT g.name) = :genreCount") // 모든 장르가 일치하는지 확인
    List<Theme> findThemesByAllGenres(List<String> genres, int genreCount);

    @Query("SELECT t FROM Theme t " +
        "WHERE (t.name LIKE %:search% OR :search IS NULL)")
    List<Theme> findThemesBySearch(String search);

    @Query("SELECT t FROM Theme t " +
        "INNER JOIN Store s ON t.store.storeCode = s.storeCode " +
        "WHERE s.storeCode = :storeCode")
    List<Theme> findByStoreCode(Integer storeCode);
}
