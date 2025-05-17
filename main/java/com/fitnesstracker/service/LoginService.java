package com.fitnesstracker.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fitnesstracker.config.DBConfig;
import com.fitnesstracker.model.UserModel;
import com.fitnesstracker.util.PasswordUtil;

/**
 * Handles user authentication and user-related queries for login and profile.
 */
public class LoginService {

    private Connection dbConn;
    private boolean connectionErr = false;

    public LoginService() {
        try {
            dbConn = DBConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            connectionErr = true;
        }
    }

    /**
     * Authenticates a user by validating username and password.
     * @param userModel The user model containing username and password input.
     * @return true if login successful, false if invalid credentials, null if DB error.
     */
    public Boolean loginUser(UserModel userModel) {
        if (connectionErr) {
            System.out.println("Error in Connection. Try again.");
            return null;
        }

        String query = "SELECT username, password FROM user WHERE username = ?";

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, userModel.getUsername());
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                return validatePassword(result, userModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return false;
    }

    /**
     * Retrieves the role of a user by username.
     * @param username Username to look up.
     * @return Role name, or null if not found or error.
     */
    public String getUserRole(String username) {
        if (connectionErr) {
            System.out.println("Error in Connection. Try again.");
            return null;
        }

        String query = "SELECT r.role FROM roles r " +
                       "JOIN user u ON r.user_id = u.user_id " +
                       "WHERE u.username = ?";

        try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves full user details by username.
     * @param username Username to look up.
     * @return UserModel populated with user details, or null if not found.
     */
    public UserModel getFullUserByUsername(String username) {
        UserModel user = null;

        try (Connection conn = DBConfig.getDbConnection()) {
            String sql = "SELECT user_id, f_name, l_name, username, email, birthday, password, image_path FROM user WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new UserModel();
                user.setId(rs.getInt("user_id"));
                user.setF_name(rs.getString("f_name"));
                user.setL_name(rs.getString("l_name"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));

                java.sql.Date birthdaySql = rs.getDate("birthday");
                if (birthdaySql != null) {
                    user.setBirthday(birthdaySql.toLocalDate());
                }

                user.setPassword(rs.getString("password"));
                user.setImage_path(rs.getString("image_path"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * Updates user profile details.
     * @param user UserModel containing updated information.
     * @return true if update successful, false otherwise.
     */
    public boolean updateUserProfile(UserModel user) {
        String sql = "UPDATE users SET f_name=?, l_name=?, email=? WHERE username=?";
        try (Connection conn = DBConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getF_name());
            stmt.setString(2, user.getL_name());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getUsername());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validates input password against decrypted database password.
     * @param result ResultSet containing stored username and password.
     * @param userModel UserModel with input credentials.
     * @return true if valid credentials, false otherwise.
     * @throws SQLException if result access fails.
     */
    private boolean validatePassword(ResultSet result, UserModel userModel) throws SQLException {
        String dbUsername = result.getString("username");
        String dbPassword = result.getString("password");

        return dbUsername.equals(userModel.getUsername())
                && PasswordUtil.decrypt(dbPassword, dbUsername).equals(userModel.getPassword());
    }
}
