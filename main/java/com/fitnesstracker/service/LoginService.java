package com.fitnesstracker.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fitnesstracker.config.DBConfig;
import com.fitnesstracker.model.UserModel;
import com.fitnesstracker.util.PasswordUtil;
import com.fitnesstracker.model.RoleModel;


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
	
	//GETTING usename and password for login procedure
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
	
	//GETING USER FOR ADMIN PAGE REDIRECTION
	
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



	/*
	 * PASSWORD VALIDATION AND DECRYPTION
	 */
	
	private boolean validatePassword(ResultSet result, UserModel userModel) throws SQLException{
		String dbUsername = result.getString("username");
		String dbPassword = result.getString("password");
		
		
		return dbUsername.equals(userModel.getUsername())
		&& PasswordUtil.decrypt(dbPassword, dbUsername).equals(userModel.getPassword());
		
	}
	
	
}