package com.fitnesstracker.service;

import com.fitnesstracker.model.FavoriteMeal;
import com.fitnesstracker.config.DBConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriteMealService {
    private Connection dbConn;

    public FavoriteMealService() {
        try {
            this.dbConn = DBConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean addToFavorites(int userId, int mealId) {
        String sql = "INSERT INTO favorite_meals (user_id, meal_id) VALUES (?, ?)";
        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, mealId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFromFavorites(int userId, int mealId) {
        String sql = "DELETE FROM favorite_meals WHERE user_id = ? AND meal_id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, mealId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isFavorite(int userId, int mealId) {
        String sql = "SELECT COUNT(*) FROM favorite_meals WHERE user_id = ? AND meal_id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, mealId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<FavoriteMeal> getFavoriteMeals(int userId) {
        List<FavoriteMeal> favorites = new ArrayList<>();
        String sql = "SELECT f.favorite_id, f.user_id, f.meal_id, m.uploadedmeals_name, m.uploadedmeal_type, m.calories, m.macros " +
                    "FROM favorite_meals f " +
                    "JOIN uploadedmeals m ON f.meal_id = m.uploadedmeals_id " +
                    "WHERE f.user_id = ?";
        
        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                FavoriteMeal favorite = new FavoriteMeal();
                favorite.setFavoriteId(rs.getInt("favorite_id"));
                favorite.setUserId(rs.getInt("user_id"));
                favorite.setMealId(rs.getInt("meal_id"));
                favorite.setMealName(rs.getString("uploadedmeals_name"));
                favorite.setMealType(rs.getString("uploadedmeal_type"));
                favorite.setCalories(rs.getInt("calories"));
                favorite.setMacros(rs.getString("macros"));
                favorites.add(favorite);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favorites;
    }
} 