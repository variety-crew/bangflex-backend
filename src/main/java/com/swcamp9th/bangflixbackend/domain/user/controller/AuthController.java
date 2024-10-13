package com.swcamp9th.bangflixbackend.domain.user.controller;

import com.swcamp9th.bangflixbackend.common.ResponseMessage;
import com.swcamp9th.bangflixbackend.domain.user.dto.*;
import com.swcamp9th.bangflixbackend.domain.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserServiceImpl userService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입 API")
    public ResponseEntity<ResponseMessage<Object>> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        return ResponseEntity.ok(new ResponseMessage<>(200, "회원 가입 성공", userService.signup(signupRequestDto)));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 API")
    public ResponseEntity<ResponseMessage<Object>> login(@Valid @RequestBody SignRequestDto signRequestDto) {
        return ResponseEntity.ok(new ResponseMessage<>(200, "로그인 성공", userService.login(signRequestDto)));
    }

    @PostMapping("/refresh")
    @Operation(summary = "엑세스 토큰 재발급 API")
    public ResponseEntity<ResponseMessage<Object>> refresh(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return ResponseEntity.ok(new ResponseMessage<>(200, "엑세스 토큰 재발급 성공", userService.refreshTokens(refreshTokenRequestDto.getRefreshToken())));
    }

    @PostMapping("/confirm-id")
    @Operation(summary = "아이디 중복체크 API")
    public ResponseEntity<ResponseMessage<Object>> confirmID(String id) {
        boolean result;
        if (id.trim().isEmpty()) {
            result = false;
        } else {
            result = userService.findId(id);
        }
        return ResponseEntity.ok(new ResponseMessage<>(200, "아이디 중복체크 성공", result));
    }

    @PostMapping("/confirm-nickname")
    @Operation(summary = "닉네임 중복체크 API")
    public ResponseEntity<ResponseMessage<Object>> confirmNickname(String nickname) {
        boolean result;
        if (nickname.trim().isEmpty()) {
            result = false;
        } else {
            result = userService.findNickName(nickname);
        }
        return ResponseEntity.ok(new ResponseMessage<>(200, "닉네임 중복체크 성공", result));
    }
}