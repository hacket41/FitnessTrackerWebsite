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
import java.util.*;
import java.util.logging.Logger;

/**
 * Servlet implementation class WednesdayController
 * Manages the Wednesday workout page, handling display and user workout progress.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/wednesday" })
public class WednesdayController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(WednesdayController.class.getName());
    private static final String SESSION_PROGRESS_KEY = "wednesdayWorkoutProgress"; // Unique session key for storing Wednesday workout progress

    public WednesdayController() {
        super();
    }

    /**
     * Handles GET requests to display the Wednesday workout page.
     * Retrieves user's workout progress from session and forwards to JSP.
     * Redirects to login if user is not authenticated.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Received GET request for /wednesday, Session ID: " + 
            (request.getSession(false) != null ? request.getSession(false).getId() : "No session"));
        
        String username = (String) SessionUtil.getAttribute(request, "username");
        if (username == null) {
            LOGGER.warning("No username in session, redirecting to login");
            response.sendRedirect(request.getContextPath() + "/login?redirect=/wednesday");
            return;
        }

        // Retrieve user's workout progress list from session
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> userProgress = (List<Map<String, Object>>) session.getAttribute(SESSION_PROGRESS_KEY);
        if (userProgress == null) {
            // Initialize empty progress list if not present in session
            userProgress = new ArrayList<>();
            session.setAttribute(SESSION_PROGRESS_KEY, userProgress);
            LOGGER.info("Initialized empty progress list in session for user: " + username);
        } else {
             LOGGER.info("Retrieved progress list from session for user: " + username + ", size: " + userProgress.size());
        }

        // Set user progress as request attribute for JSP rendering
        request.setAttribute("userProgress", userProgress);
        LOGGER.info("Set userProgress attribute in request for user: " + username + ", size: " + userProgress.size());

        // Forward request to Wednesday workout JSP page
        request.getRequestDispatcher("/WEB-INF/pages/wednesday.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for completing workouts.
     * Validates user session and workout completion.
     * Prevents duplicate workout completion for the same day.
     * Calls completeWorkout() to process the workout completion.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String username = (String) SessionUtil.getAttribute(request, "username");

        if (username == null) {
            LOGGER.warning("No username in session for POST, redirecting to login");
            response.sendRedirect(request.getContextPath() + "/login?redirect=/wednesday");
            return;
        }

        // Retrieve workout progress list from session
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> progressList = (List<Map<String, Object>>) session.getAttribute(SESSION_PROGRESS_KEY);

        if (progressList != null) {
            String todayDate = LocalDate.now().toString();
            // Check if user already completed "Core Workout" today
            for (Map<String, Object> entry : progressList) {
                if (username.equals(entry.get("username")) && todayDate.equals(entry.get("date")) && "Core Workout".equals(entry.get("workout_type"))) {
                    request.setAttribute("error", "You have already completed the Core Workout for today.");
                    request.getRequestDispatcher("/WEB-INF/pages/wednesday.jsp").forward(request, response);
                    LOGGER.warning("User " + username + " attempted to complete Core Workout again on " + todayDate);
                    return; // Stop further processing
                }
            }
        }

        if ("completeWorkout".equals(action) || "saveProgress".equals(action)) {
            // Process workout completion and save progress
            completeWorkout(request, response, username);
        } else {
            LOGGER.warning("Invalid action received: " + action);
            request.setAttribute("error", "Invalid action");
            request.getRequestDispatcher("/WEB-INF/pages/wednesday.jsp").forward(request, response);
        }
    }

    /**
     * Validates that all exercises are completed before marking the workout complete.
     * If all exercises checked, saves progress and redirects.
     * Otherwise, forwards back with error message.
     */
    private void completeWorkout(HttpServletRequest request, HttpServletResponse response, String username)
            throws ServletException, IOException {
        String[] exercises = {"plank", "russianTwists", "legRaises", "bicycleCrunches", "mountainClimbers"}; // Exercise checkbox names
        boolean allCompleted = true;
        for (String exercise : exercises) {
            String paramValue = request.getParameter(exercise);
            LOGGER.info("Checking exercise " + exercise + ": parameter value = " + (paramValue != null ? paramValue : "null"));
            if (!"on".equals(paramValue)) { // Checkbox must be checked ("on") to mark complete
                allCompleted = false;
                LOGGER.warning("Exercise " + exercise + " not completed (value: " + (paramValue != null ? paramValue : "null") + ")");
                break;
            }
        }
        if (!allCompleted) {
            // Forward back to JSP with error if not all exercises checked
            request.setAttribute("error", "Please complete all exercises before saving progress.");
            request.getRequestDispatcher("/WEB-INF/pages/wednesday.jsp").forward(request, response);
            return;
        }
        // Save progress in session and redirect to avoid resubmission
        saveProgress(request, username);
        LOGGER.info("Workout completed/progress saved for user: " + username + ", redirecting to /wednesday");
        response.sendRedirect(request.getContextPath() + "/wednesday");
    }

    /**
     * Saves the current workout progress for the user into session.
     * Creates a new entry with username, date, workout type, and completed exercises.
     * Adds entry to the session progress list.
     */
    @SuppressWarnings("unchecked")
    private void saveProgress(HttpServletRequest request, String username) {
        HttpSession session = request.getSession();
        List<Map<String, Object>> progressList = (List<Map<String, Object>>) session.getAttribute(SESSION_PROGRESS_KEY);
        if (progressList == null) {
             progressList = new ArrayList<>();
             session.setAttribute(SESSION_PROGRESS_KEY, progressList);
             LOGGER.warning("Progress list was null in saveProgress, initialized a new one for user: " + username);
        }

        // Create new progress entry map
        Map<String, Object> progressEntry = new HashMap<>();
        progressEntry.put("username", username);
        progressEntry.put("date", LocalDate.now().toString());
        progressEntry.put("workout_type", "Core Workout"); // Workout type label consistent with doPost check

        // Collect completed exercises based on checked checkboxes
        List<String> completedExercises = new ArrayList<>();
        String[] exercises = {"plank", "russianTwists", "legRaises", "bicycleCrunches", "mountainClimbers"};
        for (String exercise : exercises) {
             if ("on".equals(request.getParameter(exercise))) {
                 completedExercises.add(exercise);
             }
        }
        progressEntry.put("completed_exercises", completedExercises);

        // Add new progress entry to the list in session
        progressList.add(progressEntry);
        LOGGER.info("Added new progress entry for user: " + username + ", date: " + progressEntry.get("date") + ", completed: " + completedExercises.size() + " exercises.");
        LOGGER.info("Current progress list size in session for user: " + username + ", size: " + progressList.size());
    }

    /**
     * Utility method to list all session attributes and their values.
     * Useful for debugging session state.
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
