package org.unibl.etf.ip.sni_projekat.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.sni_projekat.model.*;
import org.unibl.etf.ip.sni_projekat.model.entities.UserEntity;
import org.unibl.etf.ip.sni_projekat.services.AuthenticationService;

@RestController
@RequestMapping("/api/authentication")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
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




}
