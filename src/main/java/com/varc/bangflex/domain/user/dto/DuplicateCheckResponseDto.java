package com.varc.bangflex.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DuplicateCheckResponseDto {
    boolean isDuplicate;
}