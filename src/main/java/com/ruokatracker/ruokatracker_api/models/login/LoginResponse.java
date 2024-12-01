package com.ruokatracker.ruokatracker_api.models.login;

import com.ruokatracker.ruokatracker_api.models.user.UserDTO;

public class LoginResponse {
    private String token;
    private UserDTO user;

    // Constructors
    public LoginResponse(String token, UserDTO user) {
        this.token = token;
        this.user = user;
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
