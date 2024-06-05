package org.unibl.etf.ip.sni_projekat.services.impl;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.sni_projekat.model.*;
import org.unibl.etf.ip.sni_projekat.model.entities.UserEntity;
import org.unibl.etf.ip.sni_projekat.repositories.UserEntityRepository;
import org.unibl.etf.ip.sni_projekat.services.AuthenticationService;
import org.unibl.etf.ip.sni_projekat.services.UserService;

import java.util.Date;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {


    @PersistenceContext
    private EntityManager entityManager;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final ModelMapper mapper;

    private final UserEntityRepository userEntityRepository;

    @Value("90000")
    private String tokenExpirationDate;

    @Value("${authorization.token.secret}")
    private String tokenSecert;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserService userService, ModelMapper mapper, UserEntityRepository userEntityRepository) {


        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.mapper = mapper;
        this.userEntityRepository = userEntityRepository;
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

    @Override
    public void register(UserRequest request) {

        UserEntity user = mapper.map(request,UserEntity.class);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActivated(false);
        user.setRole(Role.USER);
        user = userEntityRepository.saveAndFlush(user);
        entityManager.refresh(user);

    }


    private String generateJWT(JWTUser user) {
        System.out.println(user.getRole().toString());
        return Jwts.builder().setId(user.getId().toString()).setSubject(user.getUsername()).claim("role", user.getRole().toString()).setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(tokenExpirationDate))).signWith(SignatureAlgorithm.HS512, tokenSecert).compact();
    }
}
