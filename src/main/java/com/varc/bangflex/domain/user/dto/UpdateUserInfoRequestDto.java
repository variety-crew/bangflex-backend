package com.varc.bangflex.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserInfoRequestDto {
    private final String email;
    private final String nickname;
    private final String image;
}