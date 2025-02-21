package com.flat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private boolean upVote;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User votedBy;

    @ManyToOne
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaints complaint;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public boolean isUpVote() {
        return upVote;
    }

    public void setUpVote(boolean upVote) {
        this.upVote = upVote;
    }

    public User getVotedBy() {
        return votedBy;
    }

    public void setVotedBy(User votedBy) {
        this.votedBy = votedBy;
    }

    public Complaints getComplaint() {
        return complaint;
    }

    public void setComplaint(Complaints complaint) {
        this.complaint = complaint;
    }
}
