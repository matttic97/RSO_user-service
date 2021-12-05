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

    @GetMapping("")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getOneUser(@PathVariable("id") long userId){
        return userService.getUserById(userId);
    }

    /*@DeleteMapping("/remove/{id}")
    public void deleteUser(@PathVariable("id") long userId){
        userService.deleteUserById(userId);
    }*/

    @PostMapping("/new")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }
}
