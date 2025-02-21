package com.flat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flat.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "User_Flat")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true ,nullable = false)
    private String userName;

    @Column(unique = true ,nullable = false)
    private String email;

    @Column(unique = true ,nullable = false)
    private String flatCode;

    @Column(unique = true, nullable = false)
    private String password;

    @Column(nullable = false)
    private int kPoints;

    @Enumerated(EnumType.STRING)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Role role;

    @OneToMany(mappedBy = "filedBy" ,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Complaints> complaints = new ArrayList<>();

    @OneToMany(mappedBy = "votedBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Transient
    private List<Vote> votes = new ArrayList<>();

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", flatCode='" + flatCode + '\'' +
                ", password='" + password + '\'' +
                ", kPoints=" + kPoints +
                ", role=" + role +
                ", complaints=" + complaints +
                ", votes=" + votes +
                '}';
    }

    public User(long id, String userName, String email, String flatCode, String password, int kPoints, Role role, List<Complaints> complaints, List<Vote> votes) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.flatCode = flatCode;
        this.password = password;
        this.kPoints = kPoints;
        this.role = role;
        this.complaints = complaints;
        this.votes = votes;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public int getkPoints() {
        return kPoints;
    }

    public void setkPoints(int kPoints) {
        this.kPoints = kPoints;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
