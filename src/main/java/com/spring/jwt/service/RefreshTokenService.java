package com.spring.jwt.service;

import com.spring.jwt.entities.RefreshToken;
import com.spring.jwt.entities.User;
import com.spring.jwt.repositories.RefreshTokenRepository;
import com.spring.jwt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    private long refreshTokenValidity= 5 * 60 * 60 * 1000;


    public RefreshToken createRefreshToken(String userName) {

        User user=userRepository.findByEmail(userName).get();

        RefreshToken refreshToken1 = user.getRefreshToken();

        if (refreshToken1==null){
            refreshToken1 = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expiry(Instant.now().plusMillis(refreshTokenValidity))
                    .user(user)
                    .build();
            System.out.println(refreshTokenValidity);
            System.out.println(Instant.now().plusMillis(refreshTokenValidity));
            System.out.println(Instant.now());
            System.out.println(refreshToken1.getExpiry());
        }
        else {
            refreshToken1.setExpiry(Instant.now().plusMillis(refreshTokenValidity));
        }

        user.setRefreshToken(refreshToken1);



            refreshTokenRepository.save(refreshToken1);
        return refreshToken1;
    }

    public RefreshToken verifyRefreshToken(String refreshToken) {
        RefreshToken refreshTokenDb = refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RuntimeException("Given Token does not exists"));
        if (refreshTokenDb.getExpiry().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(refreshTokenDb);
            throw  new RuntimeException("Refresh Token Expired!");

        }
        return refreshTokenDb;
    }
}
