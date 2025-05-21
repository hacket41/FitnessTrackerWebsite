package com.fitnesstracker.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fitnesstracker.config.DBConfig;
import com.fitnesstracker.model.Meal;

/**
 * Servlet implementation class ProgressController
 * Handles user progress related requests such as viewing progress,
 * updating weight, and goal weight management.
 */
@WebServlet("/progress")
public class ProgressController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * Default constructor.
     */
    public ProgressController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Handles GET requests for progress page.
	 * If action parameter equals "getWeightHistory", retrieves weight history data as JSON.
	 * Otherwise, loads today's meals, total calories, calorie goal, current and goal weights 
	 * and forwards to progress JSP page.
	 * 
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		
		if (userId == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		String action = request.getParameter("action");
		if ("getWeightHistory".equals(action)) {
			getWeightHistory(request, response, userId);
			return;
		}

		try {
			List<Meal> todaysMeals = getTodaysMeals(userId);
			int totalCalories = calculateTotalCalories(todaysMeals);
			int calorieGoal = getCalorieGoal(userId);
			double currentWeight = getCurrentWeight(userId);
			double goalWeight = getGoalWeight(userId);
			
			request.setAttribute("todaysMeals", todaysMeals);
			request.setAttribute("totalCalories", totalCalories);
			request.setAttribute("calorieGoal", calorieGoal);
			request.setAttribute("currentWeight", currentWeight);
			request.setAttribute("goalWeight", goalWeight);
			
			request.getRequestDispatcher("/WEB-INF/pages/progress.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading progress page: " + e.getMessage());
		}
	}

	/**
	 * Handles POST requests for updating weight or goal weight based on action parameter.
	 * Redirects to login if user is not authenticated.
	 * 
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Integer userId = (Integer) session.getAttribute("userId");
		
		if (userId == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		String action = request.getParameter("action");
		
		if ("updateWeight".equals(action)) {
			updateWeight(request, response, userId);
		} else if ("updateGoalWeight".equals(action)) {
			updateGoalWeight(request, response, userId);
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action: " + action);
		}
	}

	/**
	 * Retrieves user's weight history from the database and returns it as JSON response.
	 * 
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @param userId ID of the authenticated user
	 * @throws IOException if an input or output error occurs
	 */
	private void getWeightHistory(HttpServletRequest request, HttpServletResponse response, int userId) throws IOException {
		try {
			List<WeightRecord> weightHistory = new ArrayList<>();
			
			try (Connection conn = DBConfig.getDbConnection();
				 PreparedStatement stmt = conn.prepareStatement(
						 "SELECT after_wt, progress_log FROM progress " +
						 "WHERE user_id = ? AND progress_type = 'Weight Update' " +
						 "ORDER BY progress_log ASC")) {
				
				stmt.setInt(1, userId);
				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						WeightRecord record = new WeightRecord();
						record.setWeight(rs.getDouble("after_wt"));
						record.setDate(rs.getString("progress_log"));
						weightHistory.add(record);
					}
				}
			}

			// Convert weight history to JSON format and send response
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			StringBuilder json = new StringBuilder();
			json.append("{\"dates\":[");
			json.append(weightHistory.stream()
					.map(r -> "\"" + r.getDate() + "\"")
					.collect(Collectors.joining(",")));
			json.append("],\"weights\":[");
			json.append(weightHistory.stream()
					.map(r -> String.valueOf(r.getWeight()))
					.collect(Collectors.joining(",")));
			json.append("]}");
			
			response.getWriter().write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving weight history");
		}
	}

	/**
	 * Updates the user's current weight by inserting a new progress record.
	 * Validates input and sends appropriate HTTP errors if input is missing or invalid.
	 * 
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @param userId ID of the authenticated user
	 * @throws IOException if an input or output error occurs
	 */
	private void updateWeight(HttpServletRequest request, HttpServletResponse response, int userId) throws IOException {
		try {
			String weightStr = request.getParameter("weight");
			if (weightStr == null || weightStr.trim().isEmpty()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Weight is required");
				return;
			}

			double weight = Double.parseDouble(weightStr);
			String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

			try (Connection conn = DBConfig.getDbConnection();
				 PreparedStatement stmt = conn.prepareStatement(
						 "INSERT INTO progress (user_id, progress_type, progress_log, after_wt) " +
						 "VALUES (?, 'Weight Update', ?, ?)")) {
				
				stmt.setInt(1, userId);
				stmt.setString(2, currentDate);
				stmt.setDouble(3, weight);
				stmt.executeUpdate();
				
				response.setContentType("text/plain");
				response.getWriter().write(String.valueOf(weight));
			}
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid weight format");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating weight");
		}
	}

	/**
	 * Updates the user's goal weight in the database.
	 * Performs input validation and returns errors for missing or invalid input.
	 * 
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @param userId ID of the authenticated user
	 * @throws IOException if an input or output error occurs
	 */
	private void updateGoalWeight(HttpServletRequest request, HttpServletResponse response, int userId) throws IOException {
		try {
			String goalWeightStr = request.getParameter("goalWeight");
			if (goalWeightStr == null || goalWeightStr.trim().isEmpty()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Goal weight is required");
				return;
			}

			double goalWeight = Double.parseDouble(goalWeightStr);
			String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

			try (Connection conn = DBConfig.getDbConnection();
				 PreparedStatement stmt = conn.prepareStatement(
						 "INSERT INTO progress (user_id, progress_type, progress_log, goal_weight) " +
						 "VALUES (?, 'Goal Weight Update', ?, ?) " +
						 "ON DUPLICATE KEY UPDATE goal_weight = ?")) {
				
				stmt.setInt(1, userId);
				stmt.setString(2, currentDate);
				stmt.setDouble(3, goalWeight);
				stmt.setDouble(4, goalWeight);
				stmt.executeUpdate();
				
				response.setContentType("text/plain");
				response.getWriter().write(String.valueOf(goalWeight));
			}
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid goal weight format");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating goal weight");
		}
	}

	/**
	 * Retrieves all meals logged today by the user.
	 * 
	 * @param userId ID of the authenticated user
	 * @return List of Meal objects for today's date
	 */
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

	/**
	 * Calculates the total calories consumed based on the list of meals.
	 * 
	 * @param meals List of Meal objects
	 * @return Total calorie sum
	 */
	private int calculateTotalCalories(List<Meal> meals) {
		return meals.stream().mapToInt(Meal::getCaloriesConsumed).sum();
	}

	/**
	 * Retrieves the calorie goal set by the user from the database.
	 * Returns a default value of 2500 if no goal is found or on error.
	 * 
	 * @param userId ID of the authenticated user
	 * @return Calorie goal integer value
	 */
	private int getCalorieGoal(int userId) {
		try (Connection conn = DBConfig.getDbConnection();
			 PreparedStatement stmt = conn.prepareStatement(
					 "SELECT calorie_goal FROM user_goals WHERE user_id = ?")) {
			
			stmt.setInt(1, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("calorie_goal");
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return 2500; // Default calorie goal if not set
	}

	/**
	 * Retrieves the latest recorded current weight of the user.
	 * Returns 0.0 if no record is found or on error.
	 * 
	 * @param userId ID of the authenticated user
	 * @return Current weight as double
	 */
	private double getCurrentWeight(int userId) {
		try (Connection conn = DBConfig.getDbConnection();
			 PreparedStatement stmt = conn.prepareStatement(
					 "SELECT after_wt FROM progress WHERE user_id = ? ORDER BY progress_id DESC LIMIT 1")) {
			
			stmt.setInt(1, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getDouble("after_wt");
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return 0.0;
	}

	/**
	 * Retrieves the latest goal weight set by the user.
	 * Returns 0.0 if no record is found or on error.
	 * 
	 * @param userId ID of the authenticated user
	 * @return Goal weight as double
	 */
	private double getGoalWeight(int userId) {
		try (Connection conn = DBConfig.getDbConnection();
			 PreparedStatement stmt = conn.prepareStatement(
					 "SELECT goal_weight FROM progress WHERE user_id = ? ORDER BY progress_id DESC LIMIT 1")) {
			
			stmt.setInt(1, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getDouble("goal_weight");
				}
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return 0.0;
	}

	/**
	 * Private inner class to represent a single weight record with weight value and date.
	 */
	private static class WeightRecord {
		private double weight;
		private String date;

		public double getWeight() {
			return weight;
		}

		public void setWeight(double weight) {
			this.weight = weight;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}
	}
}
