package com.fitnesstracker.controller;

import com.fitnesstracker.config.DBConfig;
import com.fitnesstracker.model.UploadedWorkout;
import com.fitnesstracker.service.WorkoutUploadService;
import com.fitnesstracker.util.SessionUtil;
import jakarta.servlet.*;
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
 * Servlet implementation class WorkoutController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/workout" })
public class WorkoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(WorkoutController.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WorkoutController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get user_id from username in session
		Integer userId = null;
		String username = (String) SessionUtil.getAttribute(request, "username");
		if (username != null) {
			try (Connection conn = DBConfig.getDbConnection()) {
				String sql = "SELECT user_id FROM user WHERE username = ?";
				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					pstmt.setString(1, username);
					ResultSet rs = pstmt.executeQuery();
					if (rs.next()) {
						userId = rs.getInt("user_id");
					}
				}
			} catch (SQLException | ClassNotFoundException e) {
				LOGGER.severe("Error fetching user_id for username: " + username + ", Error: " + e.getMessage());
			}
		}

		 try (Connection conn = DBConfig.getDbConnection()) {
			List<UploadedWorkout> workouts = new ArrayList<>();
			
			// First, get uploaded workouts
			WorkoutUploadService workoutService = new WorkoutUploadService(conn);
			workouts.addAll(workoutService.getAllWorkouts());
			
			// Then, get user's completed workouts if logged in
			if (userId != null) {
				String sql = "SELECT DISTINCT workout_name, workout_type, workout_duration FROM workout WHERE user_id = ? ORDER BY workout_id DESC";
				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					pstmt.setInt(1, userId);
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						UploadedWorkout workout = new UploadedWorkout();
						workout.setName(rs.getString("workout_name"));
						workout.setType(rs.getString("workout_type"));
						workout.setDuration(rs.getString("workout_duration"));
						workouts.add(workout);
					}
				}
			}
			
	            request.setAttribute("workoutList", workouts);
	            request.getRequestDispatcher("/WEB-INF/pages/workoutmain.jsp").forward(request, response);
	        } catch (Exception e) {
			LOGGER.severe("Error fetching workouts: " + e.getMessage());
	            throw new ServletException("Error fetching workouts", e);
	        }
	    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
