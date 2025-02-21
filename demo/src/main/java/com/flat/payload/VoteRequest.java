package com.flat.payload;

public class VoteRequest {
    private boolean upVote;
    private long votedById;
    private long complaintId;

    public VoteRequest() {
    }

    public VoteRequest(boolean upVote, long votedById, long complaintId) {
        this.upVote = upVote;
        this.votedById = votedById;
        this.complaintId = complaintId;
    }

    public boolean isUpVote() {
        return upVote;
    }

    public void setUpVote(boolean upVote) {
        this.upVote = upVote;
    }

    public long getVotedById() {
        return votedById;
    }

    public void setVotedById(long votedById) {
        this.votedById = votedById;
    }

    public long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(long complaintId) {
        this.complaintId = complaintId;
    }
}