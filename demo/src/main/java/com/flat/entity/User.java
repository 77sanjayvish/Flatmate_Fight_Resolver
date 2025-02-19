package com.flat.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true ,nullable = false)
    private String username;

    @Column(unique = true ,nullable = false)
    private String email;

    @Column(unique = true ,nullable = false)
    private String flatCode;

    private int karmaPoints;

    @OneToMany(mappedBy = "filedBy" ,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Complaints> complaints = new ArrayList<>();

    @OneToMany(mappedBy = "votedBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vote> votes = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFlatCode() {
        return flatCode;
    }

    public void setFlatCode(String flatCode) {
        this.flatCode = flatCode;
    }

    public int getKarmaPoints() {
        return karmaPoints;
    }

    public void setKarmaPoints(int karmaPoints) {
        this.karmaPoints = karmaPoints;
    }

    public List<Complaints> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<Complaints> complaints) {
        this.complaints = complaints;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }
}
