package com.flat.payload;

import com.flat.enums.Role;

public class UserDto {

    private String username;
    private String email;
    private String flatCode;
    private int kPoints;
    private Role role;

    public UserDto() {
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

    public UserDto(String username, String email, String flatCode, int kPoints, Role role) {
        this.username = username;
        this.email = email;
        this.flatCode = flatCode;
        this.kPoints = kPoints;
        this.role = role;
    }
}
