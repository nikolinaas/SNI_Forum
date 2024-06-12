package org.unibl.etf.ip.sni_projekat.controllers;


import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.sni_projekat.model.User;
import org.unibl.etf.ip.sni_projekat.model.entities.PermissionEntity;
import org.unibl.etf.ip.sni_projekat.services.PermissionService;

import java.security.Permission;
import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class PermissionController {


    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    public List<PermissionEntity> getUserById() {
        return permissionService.getAll();
    }
}
