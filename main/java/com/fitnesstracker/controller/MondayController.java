package com.fitnesstracker.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fitnesstracker.config.DBConfig;
import com.fitnesstracker.util.SessionUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Logger;

/**
 * Servlet implementation class MondayController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/monday" })
public class MondayController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(MondayController.class.getName());

    public MondayController() {
        super();
    }

    /**
     * Handles GET requests to display the Monday workout page.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Log request details
        LOGGER.info("Received GET request for /monday, Session ID: " + 
            (request.getSession(false) != null ? request.getSession(false).getId() : "No session"));
        
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
                        LOGGER.info("Username found in session: " + username + ", user_id: " + userId);
                    } else {
                        LOGGER.warning("No user_id found for username: " + username + 
                            ", Session attributes: " + listSessionAttributes(request));
                    }
                }
            } catch (SQLException | ClassNotFoundException e) {
                LOGGER.severe("Error fetching user_id for username: " + username + ", Error: " + e.getMessage());
            }
        } else {
            LOGGER.warning("No username in session, Session attributes: " + listSessionAttributes(request));
        }

        if (userId == null) {
            LOGGER.warning("No user_id obtained, redirecting to login. Session attributes: " +
                listSessionAttributes(request));
            response.sendRedirect(request.getContextPath() + "/login?redirect=/monday");
            return;
        }

        // Forward to JSP
        request.getRequestDispatcher("/WEB-INF/pages/monday.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for completing workouts.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
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

        if (userId == null) {
            LOGGER.warning("No user_id in session for POST, redirecting to login");
            response.sendRedirect(request.getContextPath() + "/login?redirect=/monday");
            return;
        }

        try (Connection conn = DBConfig.getDbConnection()) {
            if ("completeWorkout".equals(action) || "saveProgress".equals(action)) {
                completeWorkout(request, response, conn, userId);
            } else {
                LOGGER.warning("Invalid action received: " + action);
                request.setAttribute("error", "Invalid action");
                request.getRequestDispatcher("/WEB-INF/pages/monday.jsp").forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.severe("Database error: " + e.getMessage());
            request.setAttribute("error", "Server error occurred");
            request.getRequestDispatcher("/WEB-INF/pages/monday.jsp").forward(request, response);
        }
    }

    /**
     * Saves the progress of checked exercises for the user.
     */
    private void saveProgress(HttpServletRequest request, Connection conn, int userId) throws SQLException {
        // Clear previous progress for today to avoid duplicates
        String deleteSql = "DELETE FROM progress WHERE user_id = ? AND progress_type = ? AND progress_date = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, "Pull Workout");
            pstmt.setString(3, LocalDate.now().toString());
            pstmt.executeUpdate();
        }

        // Insert new progress for completed exercises
        String insertSql = "INSERT INTO progress (progress_type, progress_notes, progress_log, user_id, progress_date) VALUES (?, ?, ?, ?, ?)";
        String[] exercises = {"deadlifts", "assistedPullUps", "barbellRows", "barbellBicepCurls", "hammerCurls"};
        for (String exercise : exercises) {
            if ("on".equals(request.getParameter(exercise))) {
                try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                    pstmt.setString(1, "Pull Workout");
                    pstmt.setString(2, "Completed " + exercise);
                    pstmt.setString(3, "Exercise completed");
                    pstmt.setInt(4, userId);
                    pstmt.setString(5, LocalDate.now().toString());
                    pstmt.executeUpdate();
                }
            }
        }
    }

    /**
     * Marks the workout as completed and saves progress if all exercises are completed.
     */
    private void completeWorkout(HttpServletRequest request, HttpServletResponse response, Connection conn, int userId) throws ServletException, IOException, SQLException {
        String[] exercises = {"deadlifts", "assistedPullUps", "barbellRows", "barbellBicepCurls", "hammerCurls"};
        boolean allCompleted = true;
        for (String exercise : exercises) {
            if (!"on".equals(request.getParameter(exercise))) {
                allCompleted = false;
                break;
            }
        }
        if (!allCompleted) {
            request.setAttribute("error", "Please complete all exercises before saving progress.");
            request.getRequestDispatcher("/WEB-INF/pages/monday.jsp").forward(request, response);
            return;
        }
        // Save progress first
        saveProgress(request, conn, userId);
        // Update workout completion status
        String updateSql = "UPDATE workout SET workout_weight_lifted = 0 WHERE user_id = ? AND workout_name = ? AND workout_id = (SELECT MAX(workout_id) FROM workout WHERE user_id = ? AND workout_name = ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, "Pull");
            pstmt.setInt(3, userId);
            pstmt.setString(4, "Pull");
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated == 0) {
                // Insert new workout if none exists
                String insertSql = "INSERT INTO workout (workout_name, workout_type, workout_duration, workout_weight_lifted, user_id) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement insertPstmt = conn.prepareStatement(insertSql)) {
                    insertPstmt.setString(1, "Pull");
                    insertPstmt.setString(2, "Upper Back, Lats and Biceps");
                    insertPstmt.setInt(3, 45);
                    insertPstmt.setInt(4, 0); // No weights to track
                    insertPstmt.setInt(5, userId);
                    insertPstmt.executeUpdate();
                }
            }
        }
        response.sendRedirect(request.getContextPath() + "/monday");
    }

    /**
     * Lists session attributes for debugging.
     */
    private String listSessionAttributes(HttpServletRequest request) {
        StringBuilder attributes = new StringBuilder();
        HttpSession session = request.getSession(false);
        if (session != null) {
            java.util.Enumeration<String> attributeNames = session.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String name = attributeNames.nextElement();
                attributes.append(name).append("=").append(session.getAttribute(name)).append("; ");
            }
        }
        return attributes.toString();
    }
}