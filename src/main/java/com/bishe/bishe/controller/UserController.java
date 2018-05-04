package com.bishe.bishe.controller;

import com.bishe.bishe.model.User;
import com.bishe.bishe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/showuser/{id}")
    public User selectUser(@PathVariable int id){
        return userService.findUserById(id);
    }

    @RequestMapping(value = "/{username}/{password}/login",method = RequestMethod.GET)
    public String login(@PathVariable("username") String username,
                        @PathVariable("password") String password){
        if (userService.login(username, password)) {
            return "ojbk";
        }else {
            return "get out!";
        }
    }
}
