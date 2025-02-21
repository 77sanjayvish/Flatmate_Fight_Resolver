package com.flat.Service.Impl;

import com.flat.Service.VoteService;
import com.flat.entity.Vote;
import com.flat.exception.ResourceNotFoundException;
import com.flat.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Override
    public Vote createVote(Vote vote) {
        return voteRepository.save(vote);
    }

    @Override
    public Vote getVoteById(long id) {
        return voteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vote not found with id: " + id));
    }

    @Override
    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    @Override
    public Vote updateVote(long id, Vote vote) {
        Vote existingVote = getVoteById(id);
        existingVote.setUpVote(vote.isUpVote());
        return voteRepository.save(existingVote);
    }

    @Override
    public void deleteVote(long id) {
        voteRepository.deleteById(id);
    }
}
