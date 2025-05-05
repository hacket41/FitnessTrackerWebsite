package com.fitnesstracker.model;

/**
 * Meal model class that represents a meal entry in the database
 */
public class Meal {
    private int mealId;
    private String mealName;
    private String mealLogDate;
    private String mealType;
    private int caloriesConsumed;
    private int macrosGm;
    private int userId;
    
    // Constructors
    public Meal() {
    }
    
    public Meal(int mealId, String mealName, String mealLogDate, String mealType, int caloriesConsumed, int macrosGm, int userId) {
        this.mealId = mealId;
        this.mealName = mealName;
        this.mealLogDate = mealLogDate;
        this.mealType = mealType;
        this.caloriesConsumed = caloriesConsumed;
        this.macrosGm = macrosGm;
        this.userId = userId;
    }
    
    // Getters and Setters
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
    
    public String getMealLogDate() {
        return mealLogDate;
    }
    
    public void setMealLogDate(String mealLogDate) {
        this.mealLogDate = mealLogDate;
    }
    
    public String getMealType() {
        return mealType;
    }
    
    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
    
    public int getCaloriesConsumed() {
        return caloriesConsumed;
    }
    
    public void setCaloriesConsumed(int caloriesConsumed) {
        this.caloriesConsumed = caloriesConsumed;
    }
    
    public int getMacrosGm() {
        return macrosGm;
    }
    
    public void setMacrosGm(int macrosGm) {
        this.macrosGm = macrosGm;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
     
    @Override
    public String toString() {
        return "Meal [mealId=" + mealId + ", mealName=" + mealName + ", mealLogDate=" + mealLogDate + ", mealType="
                + mealType + ", caloriesConsumed=" + caloriesConsumed + ", macrosGm=" + macrosGm + ", userId=" + userId
                + "]";
    }
}