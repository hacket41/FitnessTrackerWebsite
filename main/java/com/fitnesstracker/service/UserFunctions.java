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
}
