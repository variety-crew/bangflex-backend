package com.swcamp9th.bangflixbackend.domain.user.service;

import com.swcamp9th.bangflixbackend.domain.user.dto.*;

public interface UserService {
    SignupResponseDto signup(SignupRequestDto signupRequestDto);
    SignResponseDto login(SignRequestDto signRequestDto);
    ReissueTokenResponseDto refreshTokens(String refreshToken);
    void logout(String refreshToken);
    UserInfoResponseDto findUserInfoById(String id);
}
