package org.unibl.etf.ip.sni_projekat.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.sni_projekat.model.JWTUser;
import org.unibl.etf.ip.sni_projekat.model.LoginRequest;
import org.unibl.etf.ip.sni_projekat.model.LoginResponse;
import org.unibl.etf.ip.sni_projekat.services.AuthenticationService;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
   public JWTUser login(@RequestBody LoginRequest request) {
        System.out.print("llllllloooooooffffffffin");

    return authenticationService.login(request);
    }

    @GetMapping("/login")
    public String loginget(){

        return  "login";
    }

}
