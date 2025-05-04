package com.fitnesstracker.service;

import com.fitnesstracker.config.DBConfig;
import com.fitnesstracker.model.UserModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserFunctions {
    private static final String SELECT_ALL_USERS = "SELECT u.user_id, u.username, u.email, u.f_name, u.l_name, u.image_path, r.role "
                                                  + "FROM user u "
                                                  + "LEFT JOIN roles r ON u.user_id = r.user_id";

    private static final String SELECT_USER_COUNT = "SELECT COUNT(*) AS total_users FROM user";

    public List<UserModel> getAllUsers() throws SQLException, ClassNotFoundException {
        List<UserModel> userList = new ArrayList<>();
        try (Connection connection = DBConfig.getDbConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS)) {

            while (resultSet.next()) {
                UserModel user = new UserModel();
                user.setId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setF_name(resultSet.getString("f_name"));
                user.setL_name(resultSet.getString("l_name"));
                user.setImage_path(resultSet.getString("image_path"));
                user.setRole(resultSet.getString("role"));
                userList.add(user);
            }
        }
        return userList;
    }

    public int getUserCount() throws SQLException, ClassNotFoundException {
        int count = 0;
        try (Connection connection = DBConfig.getDbConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_USER_COUNT)) {

            if (resultSet.next()) {
                count = resultSet.getInt("total_users");
            }
        }
        return count;
    }
    
    public UserModel getUserById(int userId) throws SQLException, ClassNotFoundException {
        Connection conn = DBConfig.getDbConnection();
        String sql = "SELECT * FROM user WHERE user_id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            UserModel user = new UserModel();
            user.setId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setF_name(rs.getString("f_name"));
            user.setL_name(rs.getString("l_name"));
            user.setEmail(rs.getString("email"));
            user.setBirthday(rs.getDate("birthday").toLocalDate());
            return user;
        }
        return null;
    }

    public void updateUserProfileFromAdmin(int userId, String fName, String lName, String email, String birthdayStr)
            throws SQLException, ClassNotFoundException {
        Connection conn = DBConfig.getDbConnection();
        String sql = "UPDATE user SET f_name=?, l_name=?, email=?, birthday=? WHERE user_id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, fName);
        stmt.setString(2, lName);
        stmt.setString(3, email);
        stmt.setDate(4, java.sql.Date.valueOf(birthdayStr));
        stmt.setInt(5, userId);
        stmt.executeUpdate();
    }

    public void deleteUser(int userId) throws SQLException, ClassNotFoundException {
        Connection conn = DBConfig.getDbConnection();
        String sql = "DELETE FROM user WHERE user_id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, userId);
        stmt.executeUpdate();
    }

}
