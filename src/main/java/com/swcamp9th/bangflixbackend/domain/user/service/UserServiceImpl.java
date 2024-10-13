package com.swcamp9th.bangflixbackend.domain.user.service;

import com.swcamp9th.bangflixbackend.domain.user.entity.Member;
import com.swcamp9th.bangflixbackend.domain.user.dto.*;
import com.swcamp9th.bangflixbackend.domain.user.repository.UserRepository;
import com.swcamp9th.bangflixbackend.redis.RedisService;
import com.swcamp9th.bangflixbackend.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisService redisService;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        if (userRepository.existsById(signupRequestDto.getId())) {
            throw new IllegalArgumentException("이미 존재하는 이름입니다.");
        }

        if (userRepository.existsByNickname(signupRequestDto.getNickname())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        if (userRepository.existsByEmail(signupRequestDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        Member user = Member.builder()
                .id(signupRequestDto.getId())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .nickname(signupRequestDto.getNickname())
                .email(signupRequestDto.getEmail())
                .isAdmin(signupRequestDto.getIsAdmin())
                .image(signupRequestDto.getImage())
                .build();

        userRepository.save(user);

        return new SignupResponseDto(user);
    }

    @Transactional
    public SignResponseDto login(SignRequestDto signRequestDto) {
        Member user = userRepository.findById(signRequestDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(signRequestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtUtil.createAccessToken(user);
        String refreshToken = jwtUtil.createRefreshToken(user);

        redisService.saveRefreshToken(user.getId(), refreshToken);

        return new SignResponseDto(accessToken, refreshToken);
    }

    @Transactional
    public ReissueTokenResponseDto refreshTokens(String refreshToken) {
        if (!jwtUtil.validateRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }

        Claims claims = jwtUtil.getRefreshTokenClaims(refreshToken);
        String id = claims.getSubject();

        Member user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // Redis에서 리프레시 토큰 조회
        if (!redisService.isRefreshTokenValid(id, refreshToken)) {
            throw new IllegalArgumentException("리프레시 토큰이 일치하지 않습니다.");
        }

        String newAccessToken = jwtUtil.createAccessToken(user);
        return new ReissueTokenResponseDto(newAccessToken);
    }

    public void logout(String refreshToken) {
        if (!jwtUtil.validateRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }

        Claims claims = jwtUtil.getRefreshTokenClaims(refreshToken);
        String username = claims.getSubject();

        Member user = userRepository.findById(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // Redis에서 리프레시 토큰 삭제
        redisService.deleteRefreshToken(username);
    }

    @Transactional(readOnly = true)
    public UserInfoResponseDto findUserInfoById(String id) {
        Member user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return new UserInfoResponseDto(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getImage()
        );
    }

    public boolean findId(String id) {
        return userRepository.existsById(id);
    }

    public boolean findNickName(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}