package com.flat.repository;

import com.flat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User ,Long> {
    Object findByEmail(String email);
}
