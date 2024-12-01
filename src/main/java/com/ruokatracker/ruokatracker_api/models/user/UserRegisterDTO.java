package com.ruokatracker.ruokatracker_api.models.user;

import jakarta.validation.constraints.*;

public class UserRegisterDTO {
    @NotNull(message = "Email cannot be null")
    @Size(max = 100, message = "Email must be less than 100 characters long")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must contain one uppercase letter, one lowercase letter, one digit, and one special character")
    private String password;

    // Getters and setters
    @NotNull(message = "Email cannot be null")
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

}
