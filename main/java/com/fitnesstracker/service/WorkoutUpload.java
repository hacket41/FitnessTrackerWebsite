package com.fitnesstracker.service;

import com.fitnesstracker.model.UploadedWorkout;
import java.sql.*;
import java.util.*;

/**
 * Service class to handle CRUD operations for uploaded workouts.
 */
public class WorkoutUpload {
    private Connection conn;

    /**
     * Constructor to initialize the service with a database connection.
     * 
     * @param conn The database connection to be used.
     */
    public WorkoutUpload(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new uploaded workout into the database.
     * 
     * @param workout The UploadedWorkout object containing workout details.
     * @throws SQLException If a database access error occurs.
     */
    public void insertWorkout(UploadedWorkout workout) throws SQLException {
        String sql = "INSERT INTO uploadedworkout (uploadedworkout_name, uploadedworkout_type, uploadedworkout_duration) VALUES(?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, workout.getName());
            stmt.setString(2, workout.getType());
            stmt.setString(3, workout.getDuration());
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves all uploaded workouts from the database.
     * 
     * @return List of UploadedWorkout objects representing all workouts.
     * @throws SQLException If a database access error occurs.
     */
    public List<UploadedWorkout> getAllWorkouts() throws SQLException {
        List<UploadedWorkout> workouts = new ArrayList<>();
        String sql = "SELECT * FROM uploadedworkout";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
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
