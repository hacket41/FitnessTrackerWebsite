package com.fitnesstracker.model;

/**
 * FavoriteMeal model represents a user's favorite meal with details such as:
 * - favoriteId: unique identifier for the favorite entry
 * - userId: the ID of the user who favorited the meal
 * - mealId: the ID of the meal
 * - mealName: name of the meal
 * - mealType: type/category of the meal (e.g., breakfast, lunch)
 * - calories: caloric content of the meal
 * - macros: macronutrient information in string format (e.g., protein, fat, carbs)
 */
public class FavoriteMeal {
    private int favoriteId;  // Unique ID for this favorite entry
    private int userId;      // User ID who favorited the meal
    private int mealId;      // Meal ID of the favorite meal
    private String mealName; // Name of the meal
    private String mealType; // Type/category of the meal
    private int calories;    // Calories in the meal
    private String macros;   // Macronutrient info (e.g., "P:20,F:10,C:30")

    // Default constructor
    public FavoriteMeal() {
    }

    // Getter for favoriteId
    public int getFavoriteId() {
        return favoriteId;
    }

    // Setter for favoriteId
    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    // Getter for userId
    public int getUserId() {
        return userId;
    }

    // Setter for userId
    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Getter for mealId
    public int getMealId() {
        return mealId;
    }

    // Setter for mealId
    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    // Getter for mealName
    public String getMealName() {
        return mealName;
    }

    // Setter for mealName
    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    // Getter for mealType
    public String getMealType() {
        return mealType;
    }

    // Setter for mealType
    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    // Getter for calories
    public int getCalories() {
        return calories;
    }

    // Setter for calories
    public void setCalories(int calories) {
        this.calories = calories;
    }

    // Getter for macros
    public String getMacros() {
        return macros;
    }

    // Setter for macros
    public void setMacros(String macros) {
        this.macros = macros;
    }
}
