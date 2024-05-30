package com.issuetracker.member.util;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TokenStoreManager {
    private final RedisTemplate<String, String> redisTemplate;

    public void saveRefreshToken(String memberId, String refreshToken, long timeout) {
        redisTemplate.opsForValue().set(memberId, refreshToken, timeout, TimeUnit.MILLISECONDS);
    }

    public String getRefreshToken(String memberId) {
        return redisTemplate.opsForValue().get(memberId);
    }

    public void deleteRefreshToken(String memberId) {
        redisTemplate.delete(memberId);
    }
}
