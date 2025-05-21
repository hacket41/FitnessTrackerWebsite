package com.fitnesstracker.service;

import com.fitnesstracker.model.UploadedMeal;
import java.sql.*;
import java.util.*;

/**
 * Service class to manage uploaded meals in the database.
 */
public class MealUpload {
    private final Connection conn;

    /**
     * Constructor that sets the database connection.
     * 
     * @param conn The active database connection.
     */
    public MealUpload(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new uploaded meal into the database.
     * 
     * @param meal The UploadedMeal object containing meal details.
     * @throws SQLException If any SQL error occurs.
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
     * Retrieves all uploaded meals from the database.
     * 
     * @return A list of UploadedMeal objects.
     * @throws SQLException If any SQL error occurs.
     */
    public List<UploadedMeal> getAllMeals() throws SQLException {
        List<UploadedMeal> meals = new ArrayList<>();
        String sql = "SELECT * FROM uploadedmeals";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UploadedMeal meal = new UploadedMeal();
                meal.setId(rs.getInt("uploadedmeals_id"));
                meal.setName(rs.getString("uploadedmeals_name"));
                meal.setType(rs.getString("uploadedmeal_type"));
                meal.setCalories(rs.getInt("calories"));
                meal.setMacros(rs.getString("macros"));
                meals.add(meal);
            }
        }
        return meals;
    }
}
