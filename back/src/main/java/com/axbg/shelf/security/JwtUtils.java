package com.axbg.shelf.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Component
public class JwtUtils {
    private static String secret;
    private static int expiration;

    @Value("${shelf.app.jwt.secret}")
    private void setSecret(String secretValue) {
        secret = secretValue;
    }

    @Value("${shelf.app.jwt.expiration}")
    private void setExpiration(int expirationValue) {
        expiration = expirationValue;
    }

    public static String generateJwt(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public static String getEmailFromJwt(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public static Date getIssuedAt(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getIssuedAt();
    }

    public static boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Cookie generateJwtCookie(String jwt) {
        Cookie jwtCookie = new Cookie("X-AUTH", jwt);
        jwtCookie.setPath("/");
        jwtCookie.setHttpOnly(true);
//        jwtCookie.setSecure(true);
        return jwtCookie;
    }

}
