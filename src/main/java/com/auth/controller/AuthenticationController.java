package com.auth.controller;

import com.auth.entity.UserDetails;
import com.auth.model.AuthRequest;
import com.auth.service.JWTService;
import com.auth.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final AuthenticationService authenticationService;

    private final JWTService jwtService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, AuthenticationService authenticationService, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }


    @PostMapping("/register")
    public String addNewUser(@RequestBody UserDetails userDetails) {
        return authenticationService.addUser(userDetails);
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user");
        }
    }
}
