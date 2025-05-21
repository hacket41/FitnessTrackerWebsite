package com.fitnesstracker.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.fitnesstracker.config.DBConfig;
import com.fitnesstracker.model.UserModel;

public class RegisterService {
    
    private Connection dbConn;
    
    public RegisterService() {
        try {
            // Initialize database connection
            this.dbConn = DBConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            // Print error if connection fails
            System.err.println("Database Connection error: " + ex.getMessage());
            ex.printStackTrace();        
        }
    }
    
    /**
     * Add a new user to the database.
     * @param userModel User details to insert
     * @return true if insertion successful, false if failed, null if DB connection error
     */
    public Boolean addUser(UserModel userModel) {
        // Check if database connection is available
        if (dbConn == null) {
            System.err.println("Database Connection is not available");
            return null;
        }
        
        // SQL query to insert a new user record
        String insertQuery = "INSERT INTO `user` (`f_name`, `l_name`, `username`, `email`, `birthday`, `password`, `image_path`) VALUES (?, ?, ?, ?, ?, ?, ?);";
        
        try (PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery)) {
            // Set parameters for the insert statement
            insertStmt.setString(1, userModel.getF_name());  // first name
            insertStmt.setString(2, userModel.getL_name());  // last name
            insertStmt.setString(3, userModel.getUsername()); // username
            insertStmt.setString(4, userModel.getEmail());    // email
            insertStmt.setDate(5, Date.valueOf(userModel.getBirthday())); // birthday (converted to SQL Date)
            insertStmt.setString(6, userModel.getPassword()); // password (should be hashed ideally)
            insertStmt.setString(7, "/resources/images/default.webp"); // default profile image path
            
            // Execute the insert query and return true if a row was inserted
            return insertStmt.executeUpdate() > 0;
        
        } catch (SQLException e) {
            // Handle SQL exceptions and print error messages
            System.err.println("Error during user registration: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
