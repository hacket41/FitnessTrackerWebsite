package com.fitnesstracker.model;

/**
 * Meal model class that represents a meal entry in the database
 */
public class Meal {
    private int mealId;               // Unique ID of the meal record
    private String mealName;          // Name of the meal (e.g., "Chicken Salad")
    private String mealLogDate;       // Date when the meal was logged, format: YYYY-MM-DD
    private String mealType;          // Type/category of the meal (e.g., breakfast, lunch)
    private int caloriesConsumed;     // Total calories consumed in this meal
    private int proteinGm;            // Protein grams in the meal
    private int carbsGm;              // Carbohydrate grams in the meal
    private int fatsGm;               // Fat grams in the meal
    private int userId;               // ID of the user who logged this meal

    // Default constructor
    public Meal() {
    }

    // Parameterized constructor for full initialization
    public Meal(int mealId, String mealName, String mealLogDate, String mealType,
                int caloriesConsumed, int proteinGm, int carbsGm, int fatsGm, int userId) {
        this.mealId = mealId;
        this.mealName = mealName;
        this.mealLogDate = mealLogDate;
        this.mealType = mealType;
        this.caloriesConsumed = caloriesConsumed;
        this.proteinGm = proteinGm;
        this.carbsGm = carbsGm;
        this.fatsGm = fatsGm;
        this.userId = userId;
    }

    // Getters and setters
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

    public int getProteinGm() {
        return proteinGm;
    }

    public void setProteinGm(int proteinGm) {
        this.proteinGm = proteinGm;
    }

    public int getCarbsGm() {
        return carbsGm;
    }

    public void setCarbsGm(int carbsGm) {
        this.carbsGm = carbsGm;
    }

    public int getFatsGm() {
        return fatsGm;
    }

    public void setFatsGm(int fatsGm) {
        this.fatsGm = fatsGm;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // toString override for debugging/logging
    @Override
    public String toString() {
        return "Meal [mealId=" + mealId + ", mealName=" + mealName + ", mealLogDate=" + mealLogDate +
               ", mealType=" + mealType + ", caloriesConsumed=" + caloriesConsumed + ", proteinGm=" + proteinGm +
               ", carbsGm=" + carbsGm + ", fatsGm=" + fatsGm + ", userId=" + userId + "]";
    }
}
