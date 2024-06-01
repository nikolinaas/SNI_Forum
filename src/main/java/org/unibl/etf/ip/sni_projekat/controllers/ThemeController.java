package org.unibl.etf.ip.sni_projekat.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.ip.sni_projekat.model.Theme;
import org.unibl.etf.ip.sni_projekat.services.ThemeService;

import java.util.List;

@RestController
@RequestMapping("/api/themes")
public class ThemeController {


    private final ThemeService service;

    public ThemeController(ThemeService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get")
    public List<Theme> getAll() {
        System.out.println("sve teme");
        return service.getAll();

    }

}
