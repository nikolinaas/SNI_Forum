package org.unibl.etf.ip.sni_projekat.services.impl;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.sni_projekat.model.JWTUser;
import org.unibl.etf.ip.sni_projekat.model.LoginRequest;
import org.unibl.etf.ip.sni_projekat.model.LoginResponse;
import org.unibl.etf.ip.sni_projekat.services.AuthenticationService;
import org.unibl.etf.ip.sni_projekat.services.UserService;

import java.util.Date;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {


    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final ModelMapper mapper;

    @Value("90000")
    private String tokenExpirationDate;

    @Value("${authorization.token.secret}")
    private String tokenSecert;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserService userService, ModelMapper mapper) {


        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    public JWTUser login(LoginRequest request) {

        JWTUser user = null;
        Authentication authentication = null;
        user = mapper.map(userService.findByUsername(request.getUsername()), JWTUser.class);
        try {
            System.out.println(request.getPassword());
         authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user, request.getPassword(), user.getAuthorities()));
           // JWTUser user = (JWTUser) authentication.getPrincipal();

            user.setToken(generateJWT(user));
        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return user;
    }


    private String generateJWT(JWTUser user) {
        System.out.println(user.getRole().toString());
        return Jwts.builder().setId(user.getId().toString()).setSubject(user.getUsername()).claim("role", user.getRole().toString()).setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(tokenExpirationDate))).signWith(SignatureAlgorithm.HS512, tokenSecert).compact();
    }
}
