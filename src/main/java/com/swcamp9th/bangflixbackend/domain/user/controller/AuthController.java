package com.swcamp9th.bangflixbackend.domain.user.controller;

import com.swcamp9th.bangflixbackend.common.ResponseMessage;
import com.swcamp9th.bangflixbackend.domain.user.dto.*;
import com.swcamp9th.bangflixbackend.domain.user.service.UserServiceImpl;
import com.swcamp9th.bangflixbackend.email.service.EmailService;
import com.swcamp9th.bangflixbackend.exception.DuplicateException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.RequestPart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

    private final UserServiceImpl userService;
    private final EmailService emailService;
    @PostMapping(value = "/signup", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "회원가입 API")
    public ResponseEntity<ResponseMessage<SignupResponseDto>> signup(@Valid @RequestPart(value = "signupDto") SignupRequestDto signupRequestDto, @RequestPart(value = "imgFile", required = false) MultipartFile imgFile) throws IOException {
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
    public ResponseEntity<ResponseMessage<SignResponseDto>> login(@Valid @RequestBody SignRequestDto signRequestDto) {
        return ResponseEntity.ok(new ResponseMessage<>(200, "로그인 성공", userService.login(signRequestDto)));
    }

    @PostMapping("/refresh")
    @Operation(summary = "엑세스 토큰 재발급 API")
    public ResponseEntity<ResponseMessage<ReissueTokenResponseDto>> refresh(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return ResponseEntity.ok(new ResponseMessage<>(200, "엑세스 토큰 재발급 성공", userService.refreshTokens(refreshTokenRequestDto.getRefreshToken())));
    }

    @PostMapping("/confirm-id")
    @Operation(summary = "아이디 중복체크 API")
    public ResponseEntity<ResponseMessage<DuplicateCheckResponseDto>> confirmId(@Valid @RequestBody ConfirmIdRequestDto confirmIdRequestDto) {
        DuplicateCheckResponseDto result = userService.findId(confirmIdRequestDto.getId());
        if (!result.isDuplicate()) {
            return ResponseEntity.ok(new ResponseMessage<>(200, "사용 가능한 아이디입니다", result));
        } else {
            throw new DuplicateException("중복된 아이디입니다");
        }
    }

    @PostMapping("/confirm-nickname")
    @Operation(summary = "닉네임 중복체크 API")
    public ResponseEntity<ResponseMessage<DuplicateCheckResponseDto>> confirmNickname(@Valid @RequestBody ConfirmNicknameRequestDto confirmNicknameRequestDto) {
        DuplicateCheckResponseDto result = userService.findNickName(confirmNicknameRequestDto.getNickname());
        if (!result.isDuplicate()) {
            return ResponseEntity.ok(new ResponseMessage<>(200, "사용 가능한 닉네임입니다", result));
        } else {
            throw new DuplicateException("중복된 닉네임입니다");
        }
    }

    @PostMapping("/send-email")
    @Operation(summary = "인증 이메일 발송 API")
    public ResponseEntity<ResponseMessage<Object>> sendEmail(@RequestBody EmailRequestDto emailRequestDto) throws MessagingException, UnsupportedEncodingException {
        try {
            emailService.sendSimpleMessage(emailRequestDto.getEmail());
            return ResponseEntity.ok(new ResponseMessage<>(200, "인증 이메일 발송 성공", null));
        } catch (MailException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage<>(500, "인증 이메일 발송 실패: " + e.getMessage(), null));
        }
    }

    @PostMapping("/confirm-email")
    @Operation(summary = "인증 이메일 검증 API")
    public ResponseEntity<ResponseMessage<Object>> confirmEmail(@RequestBody EmailCodeRequestDto emailCodeRequestDto) {
        return ResponseEntity.ok(new ResponseMessage<>(200, "인증 이메일 검증 성공", emailService.findEmailcode(emailCodeRequestDto)));
    }
}