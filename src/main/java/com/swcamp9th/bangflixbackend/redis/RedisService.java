package com.swcamp9th.bangflixbackend.redis;

import com.swcamp9th.bangflixbackend.redis.dto.RedisDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${refresh-token.expiration_time}")
    private Long expireTime;

    public String getRedis(RedisDto param) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String result = (String) operations.get(param.getKey());
        if (!StringUtils.hasText(result)) {
            operations.set(param.getKey(), param.getValue(), 10, TimeUnit.MINUTES);
            result = param.getValue();
        }
        return result;
    }

    public void saveRefreshToken(String id, String refreshToken) {

        redisTemplate.opsForValue().set(id, refreshToken, expireTime, TimeUnit.MILLISECONDS);
    }

    public String getRefreshToken(String id) {
        return (String) redisTemplate.opsForValue().get(id);
    }

    public void deleteRefreshToken(String id) {
        redisTemplate.delete(id);
    }

    // 리프레시 토큰 유효성 검사
    public boolean isRefreshTokenValid(String id, String refreshToken) {
        String storedRefreshToken = (String) redisTemplate.opsForValue().get(id);
        return refreshToken != null && refreshToken.equals(storedRefreshToken);
    }
}