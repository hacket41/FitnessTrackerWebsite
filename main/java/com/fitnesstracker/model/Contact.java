package com.fitnesstracker.model;

/**
 * Contact model represents a contact entity with:
 * - contactId: unique identifier for the contact
 * - username: the contact's username
 * - email: contact's email address
 * - userId: identifier linking the contact to a user
 */
public class Contact {
    private int contactId;   
    private String username; 
    private String email;     
    private int userId;      

    // Getter for contactId
    public int getContactId() {
        return contactId;
    }

    // Setter for contactId
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter for userId
    public int getUserId() {
        return userId;
    }

    // Setter for userId
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
