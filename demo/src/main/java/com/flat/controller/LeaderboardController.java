package com.flat.controller;

import com.flat.entity.User;
import com.flat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LeaderboardController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/leaderboard")
    public ResponseEntity<List<User>> getLeaderboard() {
        // Fetch users sorted by karmaPoints in descending order
        List<User> leaderboard = userRepository.findAll(Sort.by(Sort.Direction.DESC, "karmaPoints"));
        return ResponseEntity.ok(leaderboard);
    }
}