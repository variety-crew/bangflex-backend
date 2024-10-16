package com.swcamp9th.bangflixbackend.common.util;

import com.swcamp9th.bangflixbackend.domain.user.entity.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public final String AUTHORITIES_KEY = "auth";
	public static final String BEARER_PREFIX = "Bearer ";

	@Value("${access-token.expiration_time}")
	private Long accessTokenTime;
	@Value("${refresh-token.expiration_time}")
	private Long refreshTokenTime;
	@Value("${access-token.secret}")
	private String accessTokenSecret;
	@Value("${refresh-token.secret}")
	private String refreshTokenSecret;

	private Key accessTokenKey;
	private Key refreshTokenKey;
	private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	@PostConstruct
	public void init() {
		byte[] acessBytes = Base64.getDecoder().decode(accessTokenSecret);
		byte[] refreshBytes = Base64.getDecoder().decode(refreshTokenSecret);
		accessTokenKey = Keys.hmacShaKeyFor(acessBytes);
		refreshTokenKey = Keys.hmacShaKeyFor(refreshBytes);
	}

	public String createAccessToken(Member member) {
		Date now = new Date();
		return Jwts.builder()
			.setSubject(member.getId())
			.claim(AUTHORITIES_KEY, member.getRole())
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + accessTokenTime))
			.signWith(accessTokenKey, signatureAlgorithm)
			.compact();
	}

	public String createRefreshToken(Member member) {
		Date now = new Date();
		return Jwts.builder()
			.setSubject(member.getId())
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + refreshTokenTime))
			.signWith(refreshTokenKey, signatureAlgorithm)
			.compact();
	}

	public void addJwtToHeader(String token, HttpServletResponse response) {
		response.setHeader(AUTHORIZATION_HEADER, token);
	}

	public boolean validateAcessToken(String token) {
		log.debug("token in jwtUtil: {}", token);
		try {
			Jwts.parserBuilder().setSigningKey(accessTokenKey).build().parseClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException | UnsupportedJwtException e) {
			throw new RuntimeException("JWT 서명이 유효하지 않습니다.", e);
		} catch (ExpiredJwtException e) {
			throw new RuntimeException("토큰이 만료되었습니다.", e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("JWT 클레임이 비어있습니다.", e);
		}
	}

	public boolean validateRefreshToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(refreshTokenKey).build().parseClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException | UnsupportedJwtException e) {
			throw new RuntimeException("JWT 서명이 유효하지 않습니다.", e);
		} catch (ExpiredJwtException e) {
			throw new RuntimeException("토큰이 만료되었습니다.", e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("JWT 클레임이 비어있습니다.", e);
		}
	}

	public Claims getAccessTokenClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(accessTokenKey).build().parseClaimsJws(token).getBody();
	}

	public Claims getRefreshTokenClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(refreshTokenKey).build().parseClaimsJws(token).getBody();
	}

	public String getTokenFromRequest(HttpServletRequest req) {
		String bearerToken = req.getHeader(AUTHORIZATION_HEADER);
		log.debug("bearerToken in getTokenFromRequest: {}", bearerToken);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
			return bearerToken.substring(7);
		}
		return null;
	}



	public String getSubjectFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(accessTokenSecret)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}

	public String substringToken(String token) {
		if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
			return token.substring(BEARER_PREFIX.length());
		}
		throw new NullPointerException("토큰을 찾을 수 없습니다.");
	}
}