package com.fitnesstracker.service;

import com.fitnesstracker.model.FavoriteMeal;
import com.fitnesstracker.config.DBConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class to manage favorite meals operations such as
 * adding, removing, checking, and retrieving favorites for a user.
 */
public class FavoriteMealService {
    private Connection dbConn;

    /**
     * Constructs a FavoriteMealService and initializes the database connection.
     * Prints stack trace if connection fails.
     */
    public FavoriteMealService() {
        try {
            this.dbConn = DBConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a meal to the user's favorites.
     *
     * @param userId the user's ID
     * @param mealId the meal's ID to be added as favorite
     * @return true if the meal was successfully added, false otherwise
     */
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

    /**
     * Removes a meal from the user's favorites.
     *
     * @param userId the user's ID
     * @param mealId the meal's ID to be removed from favorites
     * @return true if the meal was successfully removed, false otherwise
     */
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

    /**
     * Checks if a meal is marked as favorite by the user.
     *
     * @param userId the user's ID
     * @param mealId the meal's ID to check
     * @return true if the meal is a favorite, false otherwise
     */
    public boolean isFavorite(int userId, int mealId) {
        String sql = "SELECT COUNT(*) FROM favorite_meals WHERE user_id = ? AND meal_id = ?";
        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, mealId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieves all favorite meals for a user.
     *
     * @param userId the user's ID
     * @return a List of FavoriteMeal objects representing the user's favorites
     */
    public List<FavoriteMeal> getFavoriteMeals(int userId) {
        List<FavoriteMeal> favorites = new ArrayList<>();
        String sql = "SELECT f.favorite_id, f.user_id, f.meal_id, m.uploadedmeals_name, m.uploadedmeal_type, m.calories, m.macros " +
                "FROM favorite_meals f " +
                "JOIN uploadedmeals m ON f.meal_id = m.uploadedmeals_id " +
                "WHERE f.user_id = ?";

        try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favorites;
    }
}
