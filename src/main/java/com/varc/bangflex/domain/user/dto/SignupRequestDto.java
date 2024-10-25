package com.varc.bangflex.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequestDto {

	@NotBlank(message = "아이디는 필수 입력 항목입니다.")
	private String id;

	@NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
	@Pattern(
		regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[~!@#$%^&*]).{8,}$",
		message = "비밀번호는 대소문자, 숫자, 특수문자(~!@#$%^&*)를 포함하여 8자 이상이어야 합니다."
	)
	private String password;

	@NotBlank(message = "닉네임은 필수 입력 항목입니다.")
	private String nickname;

	@NotBlank(message = "이메일은 필수 입력 항목입니다.")
	@Pattern(
		regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
		message = "유효한 이메일 형식이 아닙니다."
	)
	private String email;

	@NotBlank(message = "관리자 여부는 필수 입력 항목입니다.")
	private Boolean isAdmin;
}