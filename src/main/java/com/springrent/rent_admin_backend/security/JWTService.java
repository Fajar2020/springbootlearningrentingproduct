package com.springrent.rent_admin_backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.springrent.rent_admin_backend.models.Users;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.algorithmKey}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${expiredTimeInSeconds}")
    private int expiredTimeInSeconds;

    private Algorithm algorithm;

    private static final String USERNAME_KEY = "USERNAME";

    @PostConstruct
    public void postConstruct(){
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    public String generateJWT(Users users) {
        return JWT.create().withClaim(USERNAME_KEY, users.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiredTimeInSeconds)))
                .withIssuer(issuer).sign(algorithm);
    }

    public String getUsername(String token) {
        return JWT.decode(token).getClaim(USERNAME_KEY).asString();
    }
}
