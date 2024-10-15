package com.swcamp9th.bangflixbackend.domain.user.controller;

import com.swcamp9th.bangflixbackend.common.ResponseMessage;
import com.swcamp9th.bangflixbackend.domain.user.dto.*;
import com.swcamp9th.bangflixbackend.domain.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestPart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    private final UserServiceImpl userService;
    @PostMapping(value = "/signup", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "회원가입 API")
    public ResponseEntity<Object> signup(@Valid @RequestPart(value = "signupDto") SignupRequestDto signupRequestDto,
                                         @RequestPart(value = "imgFile", required = false) MultipartFile imgFile) throws IOException {
        if (imgFile == null) {
            log.info("AuthController signup - imgFile is null");
            userService.signupWithoutProfile(signupRequestDto);
        } else {
            log.info("AuthController signup - imgFile is NOT null");
            userService.signup(signupRequestDto, imgFile);
        }
        return ResponseEntity.ok(new ResponseMessage<>(200, "회원 가입 성공", null));
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
        return ResponseEntity.ok(new ResponseMessage<>(200, "아이디 중복체크 성공", userService.findId(id)));
    }

    @PostMapping("/confirm-nickname")
    @Operation(summary = "닉네임 중복체크 API")
    public ResponseEntity<ResponseMessage<Object>> confirmNickname(String nickname) {
        return ResponseEntity.ok(new ResponseMessage<>(200, "닉네임 중복체크 성공", userService.findNickName(nickname)));
    }
}