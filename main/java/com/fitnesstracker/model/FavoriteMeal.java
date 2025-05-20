package com.fitnesstracker.model;

public class FavoriteMeal {
    private int favoriteId;
    private int userId;
    private int mealId;
    private String mealName;
    private String mealType;
    private int calories;
    private String macros;
    
    public FavoriteMeal() {
    }
    
    // Getters and Setters
    public int getFavoriteId() {
        return favoriteId;
    }
    
    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getMealId() {
        return mealId;
    }
    
    public void setMealId(int mealId) {
        this.mealId = mealId;
    }
    
    public String getMealName() {
        return mealName;
    }
    
    public void setMealName(String mealName) {
        this.mealName = mealName;
    }
    
    public String getMealType() {
        return mealType;
    }
    
    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
    
    public int getCalories() {
        return calories;
    }
    
    public void setCalories(int calories) {
        this.calories = calories;
    }
    
    public String getMacros() {
        return macros;
    }
    
    public void setMacros(String macros) {
        this.macros = macros;
    }
} 