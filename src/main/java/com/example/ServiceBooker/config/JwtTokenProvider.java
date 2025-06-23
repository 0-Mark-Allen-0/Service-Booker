package com.example.ServiceBooker.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@Slf4j
@Component
public class JwtTokenProvider {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.ms}")
    private int jwtExpirationInMs;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken (Authentication authentication) {

        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        Date now = new Date();

        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("authorities", userPrincipal.getAuthorities().stream().map(
                        GrantedAuthority::getAuthority).collect(Collectors.toList())
                )
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromJwt (String token) {

        Claims claims = Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    public boolean validateToken (String authToken) {
        try {
            Jwts.parser().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        }
        catch (SignatureException e) {
            log.error("Invalid JWT sign");
        }
        catch (MalformedJwtException e) {
            log.error("Invalid JWT token");
        }
        catch (ExpiredJwtException e) {
            log.error("Expired JWT token");
        }
        catch (IllegalArgumentException e) {
            log.error("String is empty");
        }
        return false;
    }


}
