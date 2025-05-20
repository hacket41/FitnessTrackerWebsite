package com.fitnesstracker.model;

import java.time.LocalDate;

/**
 * Model class representing a User entity
 */
public class UserModel {
    private int userId;  // Changed from id to userId to match your actual model
    private String username;
    private String email;
    private String f_name;
    private String l_name;
    private String password;
    private LocalDate birthday;
    private String image_path;
    private String role;
    
    public UserModel() {
        // Default constructor
    }
    
    // Getters and setters
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    // Add a method to support ${user.id} in JSP
    public int getId() {
        return userId;
    }
    
    // Add a method to set id that updates userId
    public void setId(int id) {
        this.userId = id;
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
    
    public String getF_name() {
        return f_name;
    }
    
    public void setF_name(String f_name) {
        this.f_name = f_name;
    }
    
    public String getL_name() {
        return l_name;
    }
    
    public void setL_name(String l_name) {
        this.l_name = l_name;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public LocalDate getBirthday() {
        return birthday;
    }
    
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    
    public String getImage_path() {
        return image_path;
    }
    
    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
}