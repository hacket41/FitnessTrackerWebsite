package com.fitnesstracker.model;

import java.time.LocalDate;

import org.apache.catalina.User;

public class UserModel {
	
	private int id;
	private String f_name;
	private String l_name;
	private String username;
	private String email;
	private String image_path;
	private LocalDate birthday;

	//
	public ProgressModel getProgress() {
		return progress;
	}

	public void setProgress(ProgressModel progress) {
		this.progress = progress;
	}

	private String password;
	private ProgressModel progress;
	private String role;
	
	public UserModel(){
	}
	
	public UserModel(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public UserModel(int id, String f_name, String l_name, String username, String email, LocalDate birthday,  String password) {
		super();
		this.id = id;
		this.f_name = f_name;
		this.l_name = l_name;
		this.username = username;
		this.email = email;
		this.birthday = birthday;
		this.password = password;
		
	}
	
	public UserModel(String f_name, String l_name, String username, String email, LocalDate birthday,  String password) {
		this.f_name = f_name;
		this.l_name = l_name;
		this.username = username;
		this.email = email;
		this.birthday = birthday;
		this.password = password;
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getF_name() {
		return f_name;
	}

	public void setF_name(String f_name) {
		this.f_name = f_name;
	}

	public String getL_name() {
		return l_name;
	}

	public void setL_name(String l_name) {
		this.l_name = l_name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole(){
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getImage_path() {
	    return image_path;
	}

	public void setImage_path(String image_path) {
	    this.image_path = image_path;
	}

	
	
	
}
