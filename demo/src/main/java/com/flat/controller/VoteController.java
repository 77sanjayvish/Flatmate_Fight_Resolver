package com.flat.controller;

import com.flat.entity.Complaints;
import com.flat.entity.User;
import com.flat.entity.Vote;
import com.flat.payload.VoteRequest;
import com.flat.repository.ComplaintRepository;
import com.flat.repository.UserRepository;
import com.flat.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    // Create a new vote
    @PostMapping("/create")
    public ResponseEntity<Vote> createVote(@RequestBody VoteRequest voteRequest) {
        // Fetch User and Complaint entities
        User user = userRepository.findById(voteRequest.getVotedById())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Complaints complaint = complaintRepository.findById(voteRequest.getComplaintId())
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        // Create and save Vote entity
        Vote vote = new Vote();
        vote.setUpVote(voteRequest.isUpVote());
        vote.setVotedBy(user);
        vote.setComplaint(complaint);

        Vote savedVote = voteRepository.save(vote);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVote);
    }

    // Get a vote by ID
    @GetMapping("/{id}")
    public ResponseEntity<Vote> getVoteById(@PathVariable long id) {
        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vote not found"));
        return ResponseEntity.ok(vote);
    }

    // Update a vote
    @PutMapping("/{id}")
    public ResponseEntity<Vote> updateVote(@PathVariable long id, @RequestBody VoteRequest voteRequest) {
        Vote vote = voteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vote not found"));
        vote.setUpVote(voteRequest.isUpVote());
        Vote updatedVote = voteRepository.save(vote);
        return ResponseEntity.ok(updatedVote);
    }

    // Delete a vote
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVote(@PathVariable long id) {
        voteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}