package org.unibl.etf.ip.sni_projekat.services.impl;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.unibl.etf.ip.sni_projekat.model.LoginRequest;
import org.unibl.etf.ip.sni_projekat.services.AuthenticationService;

public class AuthenticationServiceImpl implements AuthenticationService {


    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager) {


        this.authenticationManager = authenticationManager;
    }

    @Override
    public Long login(LoginRequest request) {

        Authentication auth   = null;

        auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));



        return null;
    }
}
