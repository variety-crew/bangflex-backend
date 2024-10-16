package com.swcamp9th.bangflixbackend.domain.theme.repository;

import com.swcamp9th.bangflixbackend.domain.theme.entity.ThemeReaction;
import com.swcamp9th.bangflixbackend.domain.theme.entity.ThemeReactionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ThemeReactionRepository extends JpaRepository<ThemeReaction, ThemeReactionId> {

    @Query("SELECT tr FROM ThemeReaction tr JOIN FETCH tr.theme JOIN FETCH tr.member "
        + "WHERE tr.theme.themeCode = :themeCode AND tr.member.memberCode = :memberCode AND tr.active = true")
    ThemeReaction findByIds(int themeCode, int memberCode);
}
