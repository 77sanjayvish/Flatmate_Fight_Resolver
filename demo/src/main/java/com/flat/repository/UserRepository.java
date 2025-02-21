package com.flat.repository;

import com.flat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User ,Long> {
    UserDetails findByEmail(String email); // Correct findByEmail method

    Optional<Object> findById(User id);
}
