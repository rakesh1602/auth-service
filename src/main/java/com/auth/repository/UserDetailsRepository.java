package com.auth.repository;

import com.auth.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    Optional<UserDetails> findByUserName(String username);
}
