package com.varc.bangflex.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponseDto {
    private final String id;
    private final String nickname;
    private final boolean isAdmin;
    private final String image;
}
