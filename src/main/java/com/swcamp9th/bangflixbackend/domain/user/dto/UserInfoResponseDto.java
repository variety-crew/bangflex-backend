package com.swcamp9th.bangflixbackend.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponseDto {
    private final String id;
    private final String email;
    private final String nickname;
    private final String image;
}
