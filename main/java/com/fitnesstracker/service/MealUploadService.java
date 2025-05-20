package com.fitnesstracker.service;

import com.fitnesstracker.model.UploadedMeal;
import java.sql.*;
import java.util.*;

public class MealUploadService {
    private Connection conn;

    public MealUploadService(Connection conn) {
        this.conn = conn;
    }

    public void insertMeal(UploadedMeal meal) throws SQLException {
        String sql = "INSERT INTO uploaded_meals (name, type, calories, macros) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, meal.getName());
            stmt.setString(2, meal.getType());
            stmt.setInt(3, meal.getCalories());
            stmt.setString(4, meal.getMacros());
            stmt.executeUpdate();
        }
    }


    public List<UploadedMeal> getSuggestedMeals() throws SQLException {
        List<UploadedMeal> meals = new ArrayList<>();
        String sql = "SELECT * FROM uploaded_meals";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UploadedMeal meal = new UploadedMeal();
                meal.setId(rs.getInt("id"));
                meal.setName(rs.getString("name"));
                meal.setType(rs.getString("type"));
                meal.setCalories(rs.getInt("calories"));
                meal.setMacros(rs.getString("macros"));
                meals.add(meal);
            }
        }
        return meals;
    }


}
