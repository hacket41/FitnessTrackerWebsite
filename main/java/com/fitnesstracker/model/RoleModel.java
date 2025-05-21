package com.fitnesstracker.model;

public class RoleModel {
    // Role name or identifier (e.g., "admin", "user")
    private String role;

    // Constructor initializing the role
    public RoleModel(String role) {
        this.role = role;
    }

    // Getter for role
    public String getRole() {
        return role;
    }

    // Setter for role (method name corrected from setModel to setRole)
    public void setRole(String role) {
        this.role = role;
    }
}
