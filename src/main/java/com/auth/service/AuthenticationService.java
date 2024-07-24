package com.auth.service;

import com.auth.entity.UserDetails;
import com.auth.repository.UserDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AuthenticationService {

    private final UserDetailsRepository userDetailsRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UserDetailsRepository userDetailsRepository, PasswordEncoder passwordEncoder) {
        this.userDetailsRepository = userDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String addUser(UserDetails userDetails){
        log.info("Adding user with userDetails : {} ", userDetails);
        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        userDetailsRepository.save(userDetails);
        return "User added successfully";
    }
}
