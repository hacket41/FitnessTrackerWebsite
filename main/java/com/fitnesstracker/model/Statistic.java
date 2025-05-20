package com.fitnesstracker.model;

public class Statistic {
    private String category;
    private int count;

    public Statistic() {
    }

    public Statistic(String category, int count) {
        this.category = category;
        this.count = count;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
} 