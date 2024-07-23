package com.todo.app.service;

import com.todo.app.entity.UserDetails;
import com.todo.app.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserDetailsRepository userDetailsRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UserDetailsRepository userDetailsRepository, PasswordEncoder passwordEncoder) {
        this.userDetailsRepository = userDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String addUser(UserDetails userDetails){
        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        userDetailsRepository.save(userDetails);
        return "User added successfully";
    }
}
