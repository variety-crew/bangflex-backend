package com.swcamp9th.bangflixbackend.domain.user.service;

import com.swcamp9th.bangflixbackend.domain.user.dto.SignRequestDto;
import com.swcamp9th.bangflixbackend.domain.user.dto.SignResponseDto;
import com.swcamp9th.bangflixbackend.domain.user.dto.SignupRequestDto;
import com.swcamp9th.bangflixbackend.domain.user.dto.SignupResponseDto;

public interface UserService {
    SignupResponseDto signup(SignupRequestDto signupRequestDto);
    SignResponseDto login(SignRequestDto signRequestDto);
    SignResponseDto refreshTokens(String refreshToken);
    void logout(String refreshToken);
}
