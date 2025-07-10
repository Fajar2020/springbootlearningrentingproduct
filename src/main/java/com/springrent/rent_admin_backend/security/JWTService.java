package com.springrent.rent_admin_backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.springrent.rent_admin_backend.models.Users;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
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

    public boolean verifyToken(String token) {
        try {

            JWTVerifier verifier = JWT.require(algorithm)
                    .build(); // creates the verifier with default settings

            DecodedJWT jwt = verifier.verify(token); // This will throw if expired

            System.out.println("Token is valid. Subject: " + jwt.getSubject());
            return true;
        } catch (TokenExpiredException e) {
            System.out.println("Token expired at: " + e.getExpiredOn());
            return false;
        } catch (JWTVerificationException e) {
            System.out.println("Invalid token: " + e.getMessage());
            return false;
        }
    }
}
