package com.itonse.clotheslink.config.security;

import com.itonse.clotheslink.common.UserType;
import com.itonse.clotheslink.common.UserVo;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey = "secretKey";
    private final long tokenValidTime = 1000L * 60 * 60 * 3;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String userEmail, Long userId, UserType userType) {
        Claims claims = Jwts.claims()
                .setSubject(userEmail)
                .setId(userId.toString());

        claims.put("roles", userType);
        Date now = new Date();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return token;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        String userName = claims.getSubject();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("roles").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails userDetails = new User(userName, "", authorities);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public UserVo getUserInfo(String token) {
        return UserVo.builder()
                .email(parseClaims(token).getSubject())
                .id(Long.valueOf(parseClaims(token).getId()))
                .build();
    }

    private Claims parseClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody();
    }
}