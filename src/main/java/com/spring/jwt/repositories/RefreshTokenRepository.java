package com.spring.jwt.repositories;

import com.spring.jwt.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    //custom methods

    public Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
