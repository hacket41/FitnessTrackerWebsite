package com.fitnesstracker.model;

public class UploadedMeal {
    // Unique identifier for the uploaded meal
    private int id;
    // Name of the meal
    private String name;
    // Type/category of the meal (e.g., breakfast, lunch)
    private String type;
    // Caloric content of the meal
    private int calories;
    // Macronutrient details as a string (e.g., "Protein: 20g, Carbs: 30g")
    private String macros;

    // Parameterized constructor
    public UploadedMeal(int id, String name, String type, int calories, String macros) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.calories = calories;
        this.macros = macros;
    }

    // Default constructor
    public UploadedMeal() {
        // No-arg constructor
    }

    // Getter and Setter methods
    
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }

    public String getName() { 
        return name;
    }
    
    public void setName(String name) { 
        this.name = name; 
    }

    public String getType() { 
        return type; 
    }
    
    public void setType(String type) { 
        this.type = type; 
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
