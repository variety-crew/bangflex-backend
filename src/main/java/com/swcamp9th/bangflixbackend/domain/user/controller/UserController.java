package com.swcamp9th.bangflixbackend.domain.user.controller;

import com.swcamp9th.bangflixbackend.common.ResponseMessage;
import com.swcamp9th.bangflixbackend.domain.user.dto.*;
import com.swcamp9th.bangflixbackend.domain.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/login-info")
    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "회원 정보 조회(아이디, 닉네임, 이메일, 프로필 이미지) API")
    public ResponseEntity<Object> findUserInfoById() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userId;

        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            userId = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
        } else {
            // principal이 UserDetails가 아닌 경우 (ex: 익명 사용자)
            return ResponseEntity.ok(new ResponseMessage<>(401, "인증되지 않은 사용자입니다.", null));
        }
        UserInfoResponseDto userInfo = userService.findUserInfoById(userId);

        return ResponseEntity.ok(new ResponseMessage<>(200, "회원 정보 조회 성공", userInfo));
    }
}