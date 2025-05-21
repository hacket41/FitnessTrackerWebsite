package com.fitnesstracker.model;

public class Statistic {
    // Category name or label (e.g., "Workouts", "Meals")
    private String category;
    // Count or quantity associated with the category
    private int count;

    // Default constructor
    public Statistic() {
    }

    // Constructor with parameters
    public Statistic(String category, int count) {
        this.category = category;
        this.count = count;
    }

    // Getter for category
    public String getCategory() {
        return category;
    }

    // Setter for category
    public void setCategory(String category) {
        this.category = category;
    }

    // Getter for count
    public int getCount() {
        return count;
    }

    // Setter for count
    public void setCount(int count) {
        this.count = count;
    }
}
