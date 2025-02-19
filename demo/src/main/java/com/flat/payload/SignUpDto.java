package com.flat.payload;

import com.flat.enums.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SignUpDto {
    private String username;
    private String email;
    private String password;
    private String flatCode;
    private int karmaPoints;
    private Role role;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public SignUpDto() {
    }

    public SignUpDto(String username, String email, String password, String flatCode, int karmaPoints, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.flatCode = flatCode;
        this.karmaPoints = karmaPoints;
        this.role = role;
    }

    @Override
    public String toString() {
        return "SignUpDto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", flatCode='" + flatCode + '\'' +
                ", karmaPoints=" + karmaPoints +
                ", role=" + role +
                '}';
    }
}
