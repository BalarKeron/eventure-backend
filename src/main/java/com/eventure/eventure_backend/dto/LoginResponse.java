package com.eventure.eventure_backend.dto;

public class LoginResponse {
    private String role;
    private boolean approved;

    public LoginResponse(String role, boolean approved) {
        this.role = role;
        this.approved = approved;
    }

    // Getters and Setters

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }
}
