package com.spring.jwt.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@ToString
public class JwtResponse {
    private String token;
    private String refreshToken;
    private String username;
}
