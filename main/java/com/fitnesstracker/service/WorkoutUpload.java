package com.fitnesstracker.service;


import com.fitnesstracker.model.UploadedWorkout;
import java.sql.*;
import java.util.*;


public class WorkoutUpload {
	private Connection conn;
	
	public WorkoutUpload(Connection conn) {
		this.conn = conn;
	}
	
	public void insertWorkout(UploadedWorkout workout) throws SQLException{
		String sql = "INSERT INTO uploadedworkout (uploadedworkout_name, uploadedworkout_type, uploadedworkout_duration) VALUES(?,?,?)";
		try(PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setString(1, workout.getName());
			stmt.setString(2, workout.getType());
			stmt.setString(3, workout.getDuration());
			
			stmt.executeUpdate();
		}
	}
	
	public List<UploadedWorkout> getAllWorkouts() throws SQLException{
		List<UploadedWorkout> workouts = new ArrayList<>();
		String sql = "SELECT * FROM uploadedworkout";
		
		try(PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery()){
			while(rs.next()) {
				UploadedWorkout workout = new UploadedWorkout();
				workout.setId(rs.getInt("uploadedworkout_id"));
				workout.setName(rs.getString("uploadedworkout_name"));
				workout.setType(rs.getString("uploadedworkout_type"));
				workout.setDuration(rs.getString("uploadedworkout_duration"));
				
				workouts.add(workout);
			}
		}
		return workouts;
}
}
