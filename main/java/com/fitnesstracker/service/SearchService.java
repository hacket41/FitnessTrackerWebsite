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
     * Search for users by username
     * 
     * @param searchQuery the search term to look for in usernames
     * @return a list of users matching the search criteria
     */
    public List<UserModel> searchUsersByUsername(String searchQuery) throws SQLException, ClassNotFoundException {
        List<UserModel> userList = new ArrayList<>();
        
        // If search query is null or empty, return all users
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            UserFunctionsService userService = new UserFunctionsService();
            return userService.getAllUsers();
        }
        
        // SQL query to search for users by username with LIKE operator
        String searchSQL = "SELECT u.user_id, u.username, u.email, u.f_name, u.l_name, u.image_path, r.role " +
                          "FROM user u LEFT JOIN roles r ON u.user_id = r.user_id " +
                          "WHERE u.username LIKE ?";
        
        try (Connection connection = DBConfig.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(searchSQL)) {
            
            // Set the search parameter with wildcards for partial matching
            preparedStatement.setString(1, "%" + searchQuery + "%");
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    UserModel user = new UserModel();
                    user.setUserId(resultSet.getInt("user_id"));  // Changed from setId to setUserId
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));
                    user.setF_name(resultSet.getString("f_name"));
                    user.setL_name(resultSet.getString("l_name"));
                    user.setImage_path(resultSet.getString("image_path"));
                    user.setRole(resultSet.getString("role"));
                    userList.add(user);
                }
            }
        }
        
        return userList;
    }
    
    /**
     * Get a list of currently online users
     * 
     * @return list of usernames who are currently online
     */
    public List<String> getOnlineUsers() throws SQLException, ClassNotFoundException {
        List<String> onlineUsers = new ArrayList<>();
        
        // SQL query to get online users from session tracking
        String onlineUserSQL = "SELECT username FROM user_sessions WHERE is_active = true";
        
        try (Connection connection = DBConfig.getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(onlineUserSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
             
            while (resultSet.next()) {
                onlineUsers.add(resultSet.getString("username"));
            }
        }
        
        return onlineUsers;
    }
}