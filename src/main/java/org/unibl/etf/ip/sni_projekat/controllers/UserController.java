package org.unibl.etf.ip.sni_projekat.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.sni_projekat.model.User;
import org.unibl.etf.ip.sni_projekat.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/unactive")
    public List<User> getUnactiveUsers() {

        return userService.getUnacativeUsers();

    }
    @GetMapping("/active")
    public List<User> getActiveUsers() {

        return userService.getActiveUsers();

    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping("/activateUser/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User activateUser(@PathVariable Integer id,@RequestBody User user){
        return userService.activateUser(id,user);
    }
    @PutMapping("/deactivateUser/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User deactivateUser(@PathVariable Integer id,@RequestBody User user){
        return userService.activateUser(id,user);
    }

    @PutMapping("/changePermissions/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User changePermissions(@PathVariable Integer id,@RequestBody User user){
        return userService.changePermissions(id,user);
    }
}
