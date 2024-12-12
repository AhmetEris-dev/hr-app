package com.ajwalker.service;

import com.ajwalker.entity.User;
import com.ajwalker.entity.UserAuthVerifyCode;
import com.ajwalker.repository.UserAuthVerifyCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAuthVerifyCodeService {

    private final UserAuthVerifyCodeRepository userAuthVerifyCodeRepository;

    public String generateUserAuthVerifyCode(Long userId) {
        UserAuthVerifyCode userAuthVerifyCode = UserAuthVerifyCode.builder()
                .userId(userId)
                .code(generateVerifyCode())
                .build();
        return userAuthVerifyCodeRepository.save(userAuthVerifyCode).getCode();
    }
    private String generateVerifyCode() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    };

    public Optional<Long> findUserIdByAuthCode(String authCode) {
        return userAuthVerifyCodeRepository.findLastUserIdByAuthCode();
    }
}