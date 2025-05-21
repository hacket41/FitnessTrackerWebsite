package com.fitnesstracker.service;

import com.fitnesstracker.config.DBConfig;
import com.fitnesstracker.model.UserModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling search functionality in the admin panel
 */
public class SearchService {
    
    /**
     * Search for users by username.
     * 
     * @param searchQuery the search term to look for in usernames
     * @return a list of users matching the search criteria
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if database driver class not found
     */
    public List<UserModel> searchUsersByUsername(String searchQuery) throws SQLException, ClassNotFoundException {
        List<UserModel> userList = new ArrayList<>();
        
        // If search query is null or empty, return all users
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            UserFunctionsService userService = new UserFunctionsService();
            return userService.getAllUsers();
        }
        
        // SQL query to search for users by username using LIKE operator for partial matching
        String searchSQL = "SELECT u.user_id, u.username, u.email, u.f_name, u.l_name, u.image_path, r.role " +
                          "FROM user u LEFT JOIN roles r ON u.user_id = r.user_id " +
                          "WHERE u.username LIKE ?";
        
        // Establish database connection and prepare statement
        try (Connection connection = DBConfig.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(searchSQL)) {
            
            // Set the search parameter with wildcards for partial matching (e.g., '%searchTerm%')
            preparedStatement.setString(1, "%" + searchQuery + "%");
            
            // Execute the query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Loop through the results and build UserModel objects
                while (resultSet.next()) {
                    UserModel user = new UserModel();
                    user.setUserId(resultSet.getInt("user_id"));  // Set user ID
                    user.setUsername(resultSet.getString("username"));  // Set username
                    user.setEmail(resultSet.getString("email"));  // Set email
                    user.setF_name(resultSet.getString("f_name"));  // Set first name
                    user.setL_name(resultSet.getString("l_name"));  // Set last name
                    user.setImage_path(resultSet.getString("image_path"));  // Set profile image path
                    user.setRole(resultSet.getString("role"));  // Set user role
                    
                    // Add user to the result list
                    userList.add(user);
                }
            }
        }
        
        // Return the list of matched users
        return userList;
    }
    
    /**
     * Get a list of usernames who are currently online.
     * 
     * @return list of usernames who are currently online
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if database driver class not found
     */
    public List<String> getOnlineUsers() throws SQLException, ClassNotFoundException {
        List<String> onlineUsers = new ArrayList<>();
        
        // SQL query to retrieve active online users from the session tracking table
        String onlineUserSQL = "SELECT username FROM user_sessions WHERE is_active = true";
        
        // Establish database connection and execute query
        try (Connection connection = DBConfig.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(onlineUserSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
             
            // Add each online username to the list
            while (resultSet.next()) {
                onlineUsers.add(resultSet.getString("username"));
            }
        }
        
        // Return the list of online users
        return onlineUsers;
    }
}
