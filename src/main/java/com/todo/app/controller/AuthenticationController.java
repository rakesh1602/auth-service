package com.todo.app.controller;

import com.todo.app.entity.UserDetails;
import com.todo.app.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserDetails userDetails){
        return authenticationService.addUser(userDetails);
    }
}
