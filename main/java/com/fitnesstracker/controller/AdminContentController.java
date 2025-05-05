package com.fitnesstracker.controller;

import com.fitnesstracker.config.DBConfig;
import com.fitnesstracker.service.MealUpload;
import com.fitnesstracker.model.UploadedMeal;
import com.fitnesstracker.service.WorkoutUpload;
import com.fitnesstracker.model.UploadedWorkout;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
/**
 * Servlet implementation class AdminContentController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/admincontent" })
public class AdminContentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
	            MealUpload mealup = new MealUpload(conn);
	            List<UploadedMeal> meals = mealup.getAllMeals();
	            request.setAttribute("uploadedMeals", meals);
	            request.getRequestDispatcher("/WEB-INF/pages/admincontent.jsp").forward(request, response);
	        
		 	} 	catch (Exception e) {
	            throw new ServletException("Error fetching uploaded meals", e);
	        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String action = request.getParameter("action");

	    try (Connection conn = DBConfig.getDbConnection()) {
	        if ("uploadWorkout".equals(action)) {
	            String workoutName = request.getParameter("workoutName");
	            String workoutType = request.getParameter("workoutType");
	            String workoutDuration = request.getParameter("workoutDuration");

	            if (workoutName != null && workoutType != null && workoutDuration != null) {
	                UploadedWorkout workout = new UploadedWorkout();
	                workout.setName(workoutName);
	                workout.setType(workoutType);
	                workout.setDuration(workoutDuration);

	                WorkoutUpload dao = new WorkoutUpload(conn);
	                dao.insertWorkout(workout);

	                response.sendRedirect(request.getContextPath() + "/admincontent");
	                return;
	            }
	        } else {
	            // Handle meal upload
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

	                MealUpload meals = new MealUpload(conn);
	                meals.insertMeal(meal);
	            }
	        }

	        response.sendRedirect(request.getContextPath() + "/admincontent");

	    } catch (Exception e) {
	        throw new ServletException("Error uploading content", e);
	    }
	}

	
}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

