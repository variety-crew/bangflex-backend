package com.swcamp9th.bangflixbackend.email.controller;

import com.swcamp9th.bangflixbackend.email.dto.EmailDto;
import com.swcamp9th.bangflixbackend.email.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailController {

    private final EmailService emailService;

    @ResponseBody
    @PostMapping("/api/v1/auth/emailcheck")
    public String emailCheck(@RequestBody EmailDto emailDto) throws MessagingException, UnsupportedEncodingException {
        String authCode = emailService.sendSimpleMessage(emailDto.getEmail());
        log.info("EmailController emailCheck - authCode: {}", authCode);
        return authCode;
    }
}
