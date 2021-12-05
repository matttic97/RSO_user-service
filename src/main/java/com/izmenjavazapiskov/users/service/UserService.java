package com.izmenjavazapiskov.users.service;

import com.izmenjavazapiskov.users.entity.User;
import com.izmenjavazapiskov.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long userId){
        return userRepository.findByUserId(userId);
    }

    /*public void deleteUserById(long userId){
        userRepository.deleteByUserId(userId);
    }*/

    public User createUser(User user) {
        return userRepository.save(user);
    }
}
