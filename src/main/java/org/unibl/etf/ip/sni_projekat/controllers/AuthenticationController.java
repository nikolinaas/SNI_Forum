package org.unibl.etf.ip.sni_projekat.controllers;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {


    @PostMapping("/login")
    public String login() {
        System.out.println("bfhfksdbgushuiosloooogggin");
       return  "kjlfgsnhfois";
    }


}
