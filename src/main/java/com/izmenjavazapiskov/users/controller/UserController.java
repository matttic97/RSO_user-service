package com.izmenjavazapiskov.users.controller;

import com.izmenjavazapiskov.users.entity.User;
import com.izmenjavazapiskov.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAllUSers();
    }

    @PostMapping("/new")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }
}
