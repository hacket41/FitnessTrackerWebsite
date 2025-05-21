package com.fitnesstracker.service;

import com.fitnesstracker.model.UploadedMeal;

import java.sql.*;
import java.util.*;

/**
 * Service for managing uploaded meals.
 */
public class MealUploadService {
    private final Connection conn;

    /**
     * Constructs a MealUploadService with the given database connection.
     * 
     * @param conn Active database connection.
     */
    public MealUploadService(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new meal into the database.
     * 
     * @param meal The UploadedMeal to insert.
     * @throws SQLException If a database error occurs.
     */
    public void insertMeal(UploadedMeal meal) throws SQLException {
        String sql = "INSERT INTO uploadedmeals (uploadedmeals_name, uploadedmeal_type, calories, macros) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, meal.getName());
            stmt.setString(2, meal.getType());
            stmt.setInt(3, meal.getCalories());
            stmt.setString(4, meal.getMacros());
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves all uploaded meals from the database (suggested meals).
     * 
     * @return List of UploadedMeal objects.
     * @throws SQLException If a database error occurs.
     */
    public List<UploadedMeal> getSuggestedMeals() throws SQLException {
        List<UploadedMeal> meals = new ArrayList<>();
        String sql = "SELECT * FROM uploadedmeals";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                meals.add(new UploadedMeal(
                    rs.getInt("uploadedmeals_id"),
                    rs.getString("uploadedmeals_name"),
                    rs.getString("uploadedmeal_type"),
                    rs.getInt("calories"),
                    rs.getString("macros")
                ));
            }
        }
        return meals;
    }

    /**
     * Deletes a meal by its ID.
     * 
     * @param id The meal ID.
     * @throws SQLException If a database error occurs.
     */
    public void deleteMeal(int id) throws SQLException {
        String sql = "DELETE FROM uploadedmeals WHERE uploadedmeals_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves a meal by its ID.
     * 
     * @param id The meal ID.
     * @return The UploadedMeal object or null if not found.
     * @throws SQLException If a database error occurs.
     */
    public UploadedMeal getMealById(int id) throws SQLException {
        String sql = "SELECT * FROM uploadedmeals WHERE uploadedmeals_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new UploadedMeal(
                        id,
                        rs.getString("uploadedmeals_name"),
                        rs.getString("uploadedmeal_type"),
                        rs.getInt("calories"),
                        rs.getString("macros")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Updates an existing meal record.
     * 
     * @param meal The UploadedMeal with updated data.
     * @throws SQLException If a database error occurs.
     */
    public void updateMeal(UploadedMeal meal) throws SQLException {
        String sql = "UPDATE uploadedmeals SET uploadedmeals_name = ?, uploadedmeal_type = ?, calories = ?, macros = ? WHERE uploadedmeals_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, meal.getName());
            stmt.setString(2, meal.getType());
            stmt.setInt(3, meal.getCalories());
            stmt.setString(4, meal.getMacros());
            stmt.setInt(5, meal.getId());
            stmt.executeUpdate();
        }
    }
}
