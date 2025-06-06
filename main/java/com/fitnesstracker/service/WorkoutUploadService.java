package com.fitnesstracker.service;

import com.fitnesstracker.model.UploadedWorkout;
import java.sql.*;
import java.util.*;

/**
 * Service class to manage CRUD operations for uploaded workouts.
 */
public class WorkoutUploadService {
    private Connection conn;

    /**
     * Constructor to initialize with a database connection.
     * 
     * @param conn the database connection to be used
     */
    public WorkoutUploadService(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new workout into the database.
     * 
     * @param workout the UploadedWorkout object containing workout details
     * @throws SQLException if a database access error occurs
     */
    public void insertWorkout(UploadedWorkout workout) throws SQLException {
        String sql = "INSERT INTO uploadedworkout (uploadedworkout_name, uploadedworkout_type, uploadedworkout_duration) VALUES (?, ?, ?)";
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
     * @return a list of UploadedWorkout objects representing all workouts
     * @throws SQLException if a database access error occurs
     */
    public List<UploadedWorkout> getAllWorkouts() throws SQLException {
        List<UploadedWorkout> workouts = new ArrayList<>();
        String sql = "SELECT * FROM uploadedworkout";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                workouts.add(new UploadedWorkout(
                        rs.getInt("uploadedworkout_id"),
                        rs.getString("uploadedworkout_name"),
                        rs.getString("uploadedworkout_type"),
                        rs.getString("uploadedworkout_duration")
                ));
            }
        }
        return workouts;
    }

    /**
     * Deletes a workout by its ID.
     * 
     * @param id the workout ID to delete
     * @throws SQLException if a database access error occurs
     */
    public void deleteWorkout(int id) throws SQLException {
        String sql = "DELETE FROM uploadedworkout WHERE uploadedworkout_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves a workout by its ID.
     * 
     * @param id the workout ID to retrieve
     * @return the UploadedWorkout object or null if not found
     * @throws SQLException if a database access error occurs
     */
    public UploadedWorkout getWorkoutById(int id) throws SQLException {
        String sql = "SELECT * FROM uploadedworkout WHERE uploadedworkout_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UploadedWorkout(
                        id,
                        rs.getString("uploadedworkout_name"),
                        rs.getString("uploadedworkout_type"),
                        rs.getString("uploadedworkout_duration")
                );
            }
        }
        return null;
    }

    /**
     * Updates the details of an existing workout.
     * 
     * @param workout the UploadedWorkout object containing updated details
     * @throws SQLException if a database access error occurs
     */
    public void updateWorkout(UploadedWorkout workout) throws SQLException {
        String sql = "UPDATE uploadedworkout SET uploadedworkout_name=?, uploadedworkout_type=?, uploadedworkout_duration=? WHERE uploadedworkout_id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, workout.getName());
            stmt.setString(2, workout.getType());
            stmt.setString(3, workout.getDuration());
            stmt.setInt(4, workout.getId());
            stmt.executeUpdate();
        }
    }
}
