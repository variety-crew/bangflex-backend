package com.varc.bangflex.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignRequestDto {

	@NotBlank(message = "아이디를 입력하세요.")
	private String id;

	@NotBlank(message = "비밀번호를 입력하세요.")
	private String password;
}