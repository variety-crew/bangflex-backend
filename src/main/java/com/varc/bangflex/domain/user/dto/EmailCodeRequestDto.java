package com.varc.bangflex.domain.user.dto;

import lombok.Getter;

@Getter
public class EmailCodeRequestDto {
    String email;
    String code;
}
