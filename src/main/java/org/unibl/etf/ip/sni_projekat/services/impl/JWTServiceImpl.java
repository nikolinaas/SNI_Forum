package org.unibl.etf.ip.sni_projekat.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.sni_projekat.model.JWTUser;
import org.unibl.etf.ip.sni_projekat.services.BlackListService;
import org.unibl.etf.ip.sni_projekat.services.JWTService;

import java.util.Date;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {

    @Value("${authorization.token.secret}")
    private String secretKey;

    private final BlackListService blackListService;

    public JWTServiceImpl(BlackListService service) {
        this.blackListService = service;
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public Boolean isTokenValid(String token, JWTUser user) {
        String username = extractClaim(token, Claims::getSubject);
        return (user.getUsername().equals(username) && !isTokenExpired(token) && blackListService.isTokenValid(token));

    }


    @Override
    public Boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

}
