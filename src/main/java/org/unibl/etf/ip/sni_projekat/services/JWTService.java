package org.unibl.etf.ip.sni_projekat.services;

import io.jsonwebtoken.Claims;
import org.unibl.etf.ip.sni_projekat.model.JWTUser;

import java.util.function.Function;

public interface JWTService {


    Claims extractAllClaims(String token);


    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    Boolean isTokenValid(String token, JWTUser user);

    Boolean isTokenExpired(String token);
}
