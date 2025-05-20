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
			this.dbConn = DBConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			System.err.println("Database Connection error: " + ex.getMessage());
			ex.printStackTrace();		
		}
	}
	
	public Boolean addUser(UserModel userModel) {
		if (dbConn == null) {
			System.err.println("Database Connection is not available");
			return null;
		}
		
		String insertQuery = "INSERT INTO `user` (`f_name`, `l_name`, `username`, `email`, `birthday`, `password`, `image_path`) VALUES (?, ?, ?, ?, ?, ?, ?);";
		
		try (PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery)) {
			// Insert USER DETAILS
			insertStmt.setString(1, userModel.getF_name());
			insertStmt.setString(2, userModel.getL_name());
			insertStmt.setString(3, userModel.getUsername());
			insertStmt.setString(4, userModel.getEmail());
			insertStmt.setDate(5, Date.valueOf(userModel.getBirthday()));
			insertStmt.setString(6, userModel.getPassword());
			insertStmt.setString(7, "/resources/images/default.webp");
			
			return insertStmt.executeUpdate() > 0;
		
		} catch (SQLException e) {
			System.err.println("Error during user registration: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}