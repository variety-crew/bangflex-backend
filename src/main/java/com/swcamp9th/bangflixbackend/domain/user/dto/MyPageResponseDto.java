package com.swcamp9th.bangflixbackend.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyPageResponseDto {
    String nickname;
    Integer point;
    String image;
}
