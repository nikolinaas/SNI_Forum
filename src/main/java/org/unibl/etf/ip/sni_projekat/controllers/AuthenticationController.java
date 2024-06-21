package org.unibl.etf.ip.sni_projekat.controllers;


import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.sni_projekat.model.*;
import org.unibl.etf.ip.sni_projekat.model.entities.UserEntity;
import org.unibl.etf.ip.sni_projekat.services.AuthenticationService;
import org.unibl.etf.ip.sni_projekat.services.OAuth2Service;
import org.unibl.etf.ip.sni_projekat.services.SIEMService;
import org.unibl.etf.ip.sni_projekat.services.WAFService;

@RestController
@RequestMapping("/api/authentication")
@CrossOrigin(origins = "https://localhost:4200", maxAge = 3600)
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final OAuth2Service oAuth2Service;
    private final WAFService wafService;
    private final SIEMService siemService;

    public AuthenticationController(AuthenticationService authenticationService, OAuth2Service oAuth2Service, WAFService wafService, SIEMService siemService) {
        this.authenticationService = authenticationService;
        this.oAuth2Service = oAuth2Service;
        this.wafService = wafService;
        this.siemService = siemService;
    }

    @PostMapping("/login")

    public ResponseEntity<UserEntity> login(@RequestBody @Valid LoginRequest request, BindingResult bindingResult)  {
        System.out.print("llllllloooooooffffffffin");

        try {
            wafService.checkParamValidity(bindingResult);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            UserEntity useer = authenticationService.login(request);
            return new ResponseEntity<>(useer, HttpStatus.OK);
        }catch (Exception e){
            siemService.addLog(e.toString());
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        }


    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> register(@RequestBody @Valid UserRequest req, BindingResult bindingResult) {
        try {
            wafService.checkParamValidity(bindingResult);
            authenticationService.register(req);
            return new ResponseEntity<>(null,HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }


    }



    @PostMapping("/verifyCode/{id}")
    public ResponseEntity<JWTUser> verifyUser(@PathVariable Integer id, @RequestBody @Valid  CodeRequest code, BindingResult bindingResult) {

        try {
            wafService.checkParamValidity(bindingResult);
            return new ResponseEntity<>(authenticationService.verifyCode(id, code.getCode()),HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }



    }

    @PostMapping("/oauth2")
    public ResponseEntity<User> loginSuccess(@RequestBody @Valid TokenRequest token, BindingResult bindingResult) {

        try {
            wafService.checkParamValidity(bindingResult);
            return new ResponseEntity<>(authenticationService.oauth2login(token.getTokenId()),HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }


}
