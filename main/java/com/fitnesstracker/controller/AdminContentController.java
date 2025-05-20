package com.fitnesstracker.controller;

import com.fitnesstracker.config.DBConfig;
import com.fitnesstracker.service.MealUploadService;
import com.fitnesstracker.model.UploadedMeal;
import com.fitnesstracker.service.WorkoutUploadService;
import com.fitnesstracker.model.UploadedWorkout;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Servlet implementation class AdminContentController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/admincontent" })
public class AdminContentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(AdminContentController.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminContentController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = DBConfig.getDbConnection()) {
            MealUploadService mealup = new MealUploadService(conn);
            List<UploadedMeal> suggesMeals = mealup.getSuggestedMeals();
            request.setAttribute("suggestedMeals", suggesMeals);

            WorkoutUploadService workoutUp = new WorkoutUploadService(conn);
            List<UploadedWorkout> workouts = workoutUp.getAllWorkouts();
            request.setAttribute("uploadedWorkouts", workouts);

            request.getRequestDispatcher("/WEB-INF/pages/admincontent.jsp").forward(request, response);

        } catch (Exception e) {
            LOGGER.severe("Error fetching uploaded content: " + e.getMessage());
            throw new ServletException("Error fetching uploaded content", e);
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String action = request.getParameter("action");
	    LOGGER.info("Received POST request with action: " + action);

	    try (Connection conn = DBConfig.getDbConnection()) {
	        if ("uploadWorkout".equals(action)) {
	            String workoutName = request.getParameter("workoutName");
	            String workoutType = request.getParameter("workoutType");
	            String workoutDuration = request.getParameter("workoutDuration");

	            LOGGER.info("Uploading workout - Name: " + workoutName + ", Type: " + workoutType + ", Duration: " + workoutDuration);

	            if (workoutName != null && workoutType != null && workoutDuration != null) {
	                UploadedWorkout workout = new UploadedWorkout();
	                workout.setName(workoutName);
	                workout.setType(workoutType);
	                workout.setDuration(workoutDuration);

	                WorkoutUploadService work = new WorkoutUploadService(conn);
	                work.insertWorkout(workout);
	                LOGGER.info("Successfully uploaded workout: " + workoutName);
	            } else {
	                LOGGER.warning("Missing workout parameters - Name: " + workoutName + ", Type: " + workoutType + ", Duration: " + workoutDuration);
	            }
	        } else if ("uploadMeal".equals(action)) {
	            String mealName = request.getParameter("mealName");
	            String mealType = request.getParameter("mealType");
	            String caloriesStr = request.getParameter("calories");
	            String macros = request.getParameter("macros");

	            if (mealName != null && mealType != null && caloriesStr != null && macros != null) {
	                int calories = Integer.parseInt(caloriesStr);

	                UploadedMeal meal = new UploadedMeal();
	                meal.setName(mealName);
	                meal.setType(mealType);
	                meal.setCalories(calories);
	                meal.setMacros(macros);

	                MealUploadService meals = new MealUploadService(conn);
	                meals.insertMeal(meal);
	            }
	        }

	        response.sendRedirect(request.getContextPath() + "/admincontent");

	    } catch (Exception e) {
	        LOGGER.severe("Error uploading content: " + e.getMessage());
	        throw new ServletException("Error uploading content", e);
	    }
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

