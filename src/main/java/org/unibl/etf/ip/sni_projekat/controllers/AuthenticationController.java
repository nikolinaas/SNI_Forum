package org.unibl.etf.ip.sni_projekat.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.sni_projekat.model.*;
import org.unibl.etf.ip.sni_projekat.model.entities.UserEntity;
import org.unibl.etf.ip.sni_projekat.services.AuthenticationService;
import org.unibl.etf.ip.sni_projekat.services.OAuth2Service;

@RestController
@RequestMapping("/api/authentication")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final OAuth2Service oAuth2Service;

    public AuthenticationController(AuthenticationService authenticationService, OAuth2Service oAuth2Service) {
        this.authenticationService = authenticationService;
        this.oAuth2Service = oAuth2Service;
    }

    @PostMapping("/login")
    public UserEntity login(@RequestBody LoginRequest request) {
        System.out.print("llllllloooooooffffffffin");

        return authenticationService.login(request);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody UserRequest req) {
        authenticationService.register(req);
    }



    @PostMapping("/verifyCode/{id}")
    public JWTUser verifyUser(@PathVariable Integer id, @RequestBody String code) {

        return authenticationService.verifyCode(id, code);

    }

    @PostMapping("/oauth2")
    public User loginSuccess(@RequestBody String tokenId) {
// Process user information and customize your logic

        return oAuth2Service.oauth2login(tokenId);
    }


}
