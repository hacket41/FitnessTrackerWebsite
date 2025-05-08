package com.fitnesstracker.controller;

import com.fitnesstracker.model.Meal;
import com.fitnesstracker.model.UploadedMeal;
import com.fitnesstracker.config.DBConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = { "/meals", "/addMeal", "/updateWater", "/deleteMeal" })
public class MealController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MealController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            List<Meal> todaysMeals = getTodaysMeals(userId);
            int totalCalories = calculateTotalCalories(todaysMeals);
            double waterIntake = getWaterIntakeFromDatabase(userId, session);
            List<UploadedMeal> suggesMeals = getSuggestedMeals();

            request.setAttribute("todaysMeals", todaysMeals);
            request.setAttribute("totalCalories", totalCalories);
            request.setAttribute("waterIntake", waterIntake);
            request.setAttribute("suggestedMeals", suggesMeals);
            request.getRequestDispatcher("/WEB-INF/pages/meals.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading meals page: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String requestURI = request.getRequestURI();
        if (requestURI.contains("/addMeal")) {
            addMeal(request, response, userId);
        } else if (requestURI.contains("/updateWater")) {
            updateWaterIntake(request, response, session, userId);
        } else if (requestURI.contains("/deleteMeal")) {
            deleteMeal(request, response, userId);
        } else {
            doGet(request, response);
        }
    }

    private void addMeal(HttpServletRequest request, HttpServletResponse response, int userId) throws IOException {
        String mealName = request.getParameter("mealName");
        String proteinStr = request.getParameter("protein");
        String carbsStr = request.getParameter("carbs");
        String fatsStr = request.getParameter("fats");
        String caloriesStr = request.getParameter("calories");
        String hour = request.getParameter("hour");
        String minute = request.getParameter("minute");
        String ampm = request.getParameter("ampm");

        if (mealName == null || mealName.trim().isEmpty() || hour == null || minute == null || ampm == null) {
            response.sendRedirect(request.getContextPath() + "/meals?error=invalid_input");
            return;
        }

        try {
            int protein = parseIntOrDefault(proteinStr, 0);
            int carbs = parseIntOrDefault(carbsStr, 0);
            int fats = parseIntOrDefault(fatsStr, 0);
            int calories = parseIntOrDefault(caloriesStr, (protein * 4) + (carbs * 4) + (fats * 9));

            String mealType = determineMealType(hour, ampm, protein, carbs, fats);
            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            try (Connection conn = DBConfig.getDbConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO meal (meal_name, meal_log_date, meal_type, calories_consumed, protein_gm, carbs_gm, fats_gm, user_id) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

                stmt.setString(1, mealName);
                stmt.setString(2, currentDate);
                stmt.setString(3, mealType);
                stmt.setInt(4, calories);
                stmt.setInt(5, protein);
                stmt.setInt(6, carbs);
                stmt.setInt(7, fats);
                stmt.setInt(8, userId);

                stmt.executeUpdate();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/meals?error=database_error");
                return;
            }

            response.sendRedirect(request.getContextPath() + "/meals");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/meals?error=invalid_input");
        }
    }

    private void updateWaterIntake(HttpServletRequest request, HttpServletResponse response, HttpSession session, int userId) throws IOException {
        try {
            String action = request.getParameter("action");
            double currentIntake = getWaterIntakeFromDatabase(userId, session);

            if ("increase".equals(action)) {
                currentIntake += 0.1;
            } else if ("decrease".equals(action) && currentIntake >= 0.1) {
                currentIntake -= 0.1;
            }

            currentIntake = Math.round(currentIntake * 10.0) / 10.0;
            session.setAttribute("waterIntake", currentIntake);

            try (Connection conn = DBConfig.getDbConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO water_intake (user_id, intake_date, litres) VALUES (?, ?, ?) " +
                         "ON DUPLICATE KEY UPDATE litres = ?")) {
                String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                stmt.setInt(1, userId);
                stmt.setString(2, currentDate);
                stmt.setDouble(3, currentIntake);
                stmt.setDouble(4, currentIntake);
                stmt.executeUpdate();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid water intake update");
                return;
            }

            response.setContentType("text/plain");
            response.getWriter().write(String.valueOf(currentIntake));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid water intake update");
        }
    }

    private void deleteMeal(HttpServletRequest request, HttpServletResponse response, int userId) throws IOException {
        String mealIdStr = request.getParameter("mealId");
        
        if (mealIdStr == null || mealIdStr.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Meal ID is required");
            return;
        }

        try {
            int mealId = Integer.parseInt(mealIdStr);
            
            try (Connection conn = DBConfig.getDbConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "DELETE FROM meal WHERE meal_id = ? AND user_id = ?")) {
                
                stmt.setInt(1, mealId);
                stmt.setInt(2, userId);
                
                int rowsAffected = stmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Meal not found or unauthorized");
                }
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid meal ID format");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred");
        }
    }

    private List<Meal> getTodaysMeals(int userId) {
        List<Meal> meals = new ArrayList<>();
        String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        try (Connection conn = DBConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM meal WHERE user_id = ? AND meal_log_date = ? ORDER BY meal_id DESC")) {

            stmt.setInt(1, userId);
            stmt.setString(2, todayDate);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Meal meal = new Meal();
                    meal.setMealId(rs.getInt("meal_id"));
                    meal.setMealName(rs.getString("meal_name"));
                    meal.setMealLogDate(rs.getString("meal_log_date"));
                    meal.setMealType(rs.getString("meal_type"));
                    meal.setCaloriesConsumed(rs.getInt("calories_consumed"));
                    meal.setProteinGm(rs.getInt("protein_gm"));
                    meal.setCarbsGm(rs.getInt("carbs_gm"));
                    meal.setFatsGm(rs.getInt("fats_gm"));
                    meal.setUserId(rs.getInt("user_id"));
                    meals.add(meal);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return meals;
    }

    private double getWaterIntakeFromDatabase(int userId, HttpSession session) {
        Double waterIntake = (Double) session.getAttribute("waterIntake");
        if (waterIntake == null) {
            String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            try (Connection conn = DBConfig.getDbConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "SELECT litres FROM water_intake WHERE user_id = ? AND intake_date = ?")) {
                stmt.setInt(1, userId);
                stmt.setString(2, todayDate);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        waterIntake = rs.getDouble("litres");
                    } else {
                        waterIntake = 0.0;
                    }
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                waterIntake = 0.0;
            }
            session.setAttribute("waterIntake", waterIntake);
        }
        return waterIntake;
    }

    private int calculateTotalCalories(List<Meal> meals) {
        return meals.stream().mapToInt(Meal::getCaloriesConsumed).sum();
    }

    private String determineMealType(String hour, String ampm, int protein, int carbs, int fats) {
        int hourInt = Integer.parseInt(hour);

        if (hourInt >= 5 && hourInt <= 9 && "AM".equals(ampm)) return "Breakfast";
        if ((hourInt >= 11 && hourInt <= 12 && "AM".equals(ampm)) || (hourInt >= 1 && hourInt <= 2 && "PM".equals(ampm)))
            return "Lunch";
        if (hourInt >= 6 && hourInt <= 9 && "PM".equals(ampm)) return "Dinner";

        if (protein > carbs && protein > fats) return "Protein(Cut)";
        if (carbs > protein && carbs > fats) return "Carbs(Bulk)";
        if (fats > protein && fats > carbs) return "Fats";

        return "Snack";
    }

    private int parseIntOrDefault(String value, int defaultValue) {
        return value != null && !value.trim().isEmpty() ? Integer.parseInt(value) : defaultValue;
    }

    private List<UploadedMeal> getSuggestedMeals() {
        List<UploadedMeal> suggestedMeals = new ArrayList<>();

        try (Connection conn = DBConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM uploadedmeals");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                UploadedMeal meal = new UploadedMeal();
                meal.setId(rs.getInt("uploadedmeals_id"));
                meal.setName(rs.getString("uploadedmeals_name"));
                meal.setType(rs.getString("uploadedmeal_type"));
                meal.setCalories(rs.getInt("calories"));
                meal.setMacros(rs.getString("macros"));
                suggestedMeals.add(meal);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return suggestedMeals;
    }
}
