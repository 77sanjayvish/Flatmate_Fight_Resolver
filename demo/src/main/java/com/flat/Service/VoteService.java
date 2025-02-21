package com.flat.Service;

import com.flat.entity.Vote;

import java.util.List;

public interface VoteService {
    Vote createVote(Vote vote);
    Vote getVoteById(long id);
    List<Vote> getAllVotes();
    void deleteVote(long id);
    Vote updateVote(long id, Vote vote);
}