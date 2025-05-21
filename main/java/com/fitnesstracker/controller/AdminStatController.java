package com.fitnesstracker.controller;

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
import java.util.ArrayList;
import java.util.List;

import com.fitnesstracker.config.DBConfig;
import com.fitnesstracker.model.Statistic;

/**
 * Servlet implementation class AdminStatController
 * Handles the retrieval and display of statistical data for the admin dashboard.
 */
@WebServlet(urlPatterns = { "/adminstat" })
public class AdminStatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * Default constructor for AdminStatController.
     */
    public AdminStatController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Handles GET requests to display statistics on the admin dashboard.
	 * Retrieves user, meal, workout, and progress data, sets them as request attributes,
	 * and forwards the request to the adminstat.jsp page for rendering.
	 *
	 * @param request  the HttpServletRequest object
	 * @param response the HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Get basic statistics
			int newUsers = getNewUsersCount();
			int mealPlans = getMealPlansCount();
			int workoutRoutines = getWorkoutRoutinesCount();
			int progress = getProgressPercentage();
			
			// Get weekly active users data
			List<Integer> weeklyActiveUsers = getWeeklyActiveUsers();
			
			// Get meal plan engagement data
			List<Integer> mealPlanEngagement = getMealPlanEngagement();
			
			// Get user engagement breakdown
			List<Statistic> userEngagement = getUserEngagementBreakdown();
			
			// Set attributes for the JSP
			request.setAttribute("newUsers", newUsers);
			request.setAttribute("mealPlans", mealPlans);
			request.setAttribute("workoutRoutines", workoutRoutines);
			request.setAttribute("progress", progress);
			request.setAttribute("weeklyActiveUsers", weeklyActiveUsers);
			request.setAttribute("mealPlanEngagement", mealPlanEngagement);
			request.setAttribute("userEngagement", userEngagement);
			
			// Forward to the JSP
			request.getRequestDispatcher("/WEB-INF/pages/adminstat.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading statistics: " + e.getMessage());
		}
	}

	/**
	 * Handles POST requests by delegating to the doGet method.
	 *
	 * @param request  the HttpServletRequest object
	 * @param response the HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * Retrieves the number of new users registered today.
	 *
	 * @return the count of new users
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private int getNewUsersCount() throws SQLException, ClassNotFoundException {
		String sql = "SELECT COUNT(*) FROM user WHERE DATE(birthday) = CURDATE()";
		try (Connection conn = DBConfig.getDbConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		}
		return 0;
	}

	/**
	 * Retrieves the total number of uploaded meal plans.
	 *
	 * @return the count of uploaded meals
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private int getMealPlansCount() throws SQLException, ClassNotFoundException {
		String sql = "SELECT COUNT(*) FROM uploadedmeals";
		try (Connection conn = DBConfig.getDbConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		}
		return 0;
	}

	/**
	 * Retrieves the total number of uploaded workout routines.
	 *
	 * @return the count of uploaded workouts
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private int getWorkoutRoutinesCount() throws SQLException, ClassNotFoundException {
		String sql = "SELECT COUNT(*) FROM uploadedworkout";
		try (Connection conn = DBConfig.getDbConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		}
		return 0;
	}

	/**
	 * Calculates the average progress percentage from the progress table.
	 *
	 * @return the average progress percentage
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private int getProgressPercentage() throws SQLException, ClassNotFoundException {
		String sql = "SELECT AVG((after_wt - before_wt) / before_wt * 100) FROM progress";
		try (Connection conn = DBConfig.getDbConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return (int) rs.getDouble(1);
			}
		}
		return 0;
	}

	/**
	 * Retrieves the number of active users for each of the last 7 days.
	 *
	 * @return a list of integers representing daily user counts
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private List<Integer> getWeeklyActiveUsers() throws SQLException, ClassNotFoundException {
		List<Integer> weeklyData = new ArrayList<>();
		String sql = "SELECT COUNT(*) FROM user " +
					"WHERE birthday BETWEEN DATE_SUB(CURDATE(), INTERVAL 7 DAY) AND CURDATE() " +
					"GROUP BY DATE(birthday) ORDER BY birthday";
		
		try (Connection conn = DBConfig.getDbConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				weeklyData.add(rs.getInt(1));
			}
		}
		return weeklyData;
	}

	/**
	 * Retrieves the count of meal plan uploads per day.
	 *
	 * @return a list of integers representing daily meal plan uploads
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private List<Integer> getMealPlanEngagement() throws SQLException, ClassNotFoundException {
		List<Integer> engagementData = new ArrayList<>();
		String sql = "SELECT COUNT(*) FROM uploadedmeals " +
					"GROUP BY DATE(CURDATE())";
		
		try (Connection conn = DBConfig.getDbConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				engagementData.add(rs.getInt(1));
			}
		}
		return engagementData;
	}

	/**
	 * Retrieves a breakdown of user engagement statistics including new and total users.
	 *
	 * @return a list of Statistic objects containing category and count
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private List<Statistic> getUserEngagementBreakdown() throws SQLException, ClassNotFoundException {
		List<Statistic> engagementData = new ArrayList<>();
		String sql = "SELECT 'New Users' as category, COUNT(*) as count FROM user WHERE birthday >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
					"UNION ALL " +
					"SELECT 'Total Users' as category, COUNT(*) as count FROM user";
		
		try (Connection conn = DBConfig.getDbConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Statistic stat = new Statistic();
				stat.setCategory(rs.getString("category"));
				stat.setCount(rs.getInt("count"));
				engagementData.add(stat);
			}
		}
		return engagementData;
	}
}
