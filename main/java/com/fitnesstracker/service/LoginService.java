package com.fitnesstracker.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fitnesstracker.config.DBConfig;
import com.fitnesstracker.model.UserModel;
import com.fitnesstracker.util.PasswordUtil;


/**
 * VERIFICATION FOR LOGIN PAGE; DETAILS username and pasword for login after registration.
 */
public class LoginService {
	
	private Connection dbConn;
	private boolean connectionErr = false;
	
	
	public LoginService() {
		try {
			dbConn = DBConfig.getDbConnection();
		}
		catch(SQLException | ClassNotFoundException e){
			e.printStackTrace();
			connectionErr = true;
		}
	}
	
	
	public Boolean loginUser(UserModel userModel) {
		
		if(connectionErr) {
			System.out.println("Error in Connection. Try again.");
			return null;
		}
		
		String query = "SELECT username, password FROM user WHERE username = ?";
		try(PreparedStatement stmt = dbConn.prepareStatement(query)){
			stmt.setString(1, userModel.getUsername());
			ResultSet result = stmt.executeQuery();
			
			//
			if(result.next()) {
				return validatePassword(result, userModel);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return false;
	}
	
	
	
	/*
	 * PASSWORD VALIDATION
	 */
	
	private boolean validatePassword(ResultSet result, UserModel userModel) throws SQLException{
		String dbUsername = result.getString("username");
		String dbPassword = result.getString("password");
		
		
		return dbUsername.equals(userModel.getUsername())
		&& PasswordUtil.decrypt(dbPassword, dbUsername).equals(userModel.getPassword());
		
	}
}