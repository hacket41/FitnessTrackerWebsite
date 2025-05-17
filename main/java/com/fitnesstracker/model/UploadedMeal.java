package com.fitnesstracker.model;

public class UploadedMeal {
    private int id;
    private String name;
    private String type;
    private int calories;
    private String macros;

   
    public int getId(){ 
    	return id; 
    }
    
    public void setId(int id){ 
    	this.id = id; 
    }

    public String getName(){ 
    	return name;
    }
    
   
    public void setName(String name){ 
    	this.name = name; 
    }

    public String getType() { 
    	return type; 
    }
    
    
    public void setType(String type){ 
    	this.type = type; 
    }

    public int getCalories() { 
    	return calories; 
    }
    
    
    public void setCalories(int calories){ 
    	this.calories = calories; 
    }

    public String getMacros() { 
    	return macros; 
    }
    
    
    public void setMacros(String macros){ 
    	this.macros = macros; 
    }
}
