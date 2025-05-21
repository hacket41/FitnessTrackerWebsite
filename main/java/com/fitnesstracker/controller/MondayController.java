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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Servlet implementation class MondayController
 * 
 * Handles HTTP requests related to Monday's workout tracking.
 * Supports displaying workout progress and marking workouts as completed.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/monday" })
public class MondayController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(MondayController.class.getName());
    private static final String SESSION_PROGRESS_KEY = "mondayWorkoutProgress";

    /**
     * Default constructor.
     */
    public MondayController() {
        super();
    }

    /**
     * Handles GET requests to display the Monday workout page.
     * Checks for user session and retrieves workout progress from session attributes.
     * Forwards the request to the Monday JSP page.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Received GET request for /monday, Session ID: " + 
            (request.getSession(false) != null ? request.getSession(false).getId() : "No session"));
        
        String username = (String) SessionUtil.getAttribute(request, "username");
        if (username == null) {
            LOGGER.warning("No username in session, redirecting to login");
            response.sendRedirect(request.getContextPath() + "/login?redirect=/monday");
            return;
        }

        // Get user's progress from session
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> userProgress = (List<Map<String, Object>>) session.getAttribute(SESSION_PROGRESS_KEY);
        if (userProgress == null) {
            userProgress = new ArrayList<>();
            session.setAttribute(SESSION_PROGRESS_KEY, userProgress);
            LOGGER.info("Initialized empty progress list in session for user: " + username);
        } else {
             LOGGER.info("Retrieved progress list from session for user: " + username + ", size: " + userProgress.size());
        }
        
        request.setAttribute("userProgress", userProgress);
        LOGGER.info("Set userProgress attribute in request for user: " + username + ", size: " + userProgress.size());

        request.getRequestDispatcher("/WEB-INF/pages/monday.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for completing workouts.
     * Validates user session and checks if workout for the day is already completed.
     * Calls completeWorkout() to mark workout completion if valid.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String username = (String) SessionUtil.getAttribute(request, "username");

        if (username == null) {
            LOGGER.warning("No username in session for POST, redirecting to login");
            response.sendRedirect(request.getContextPath() + "/login?redirect=/monday");
            return;
        }

        // Check if workout for today is already completed
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> progressList = (List<Map<String, Object>>) session.getAttribute(SESSION_PROGRESS_KEY);

        if (progressList != null) {
            String todayDate = LocalDate.now().toString();
            for (Map<String, Object> entry : progressList) {
                 // Check for username, date, and workout type
                if (username.equals(entry.get("username")) && todayDate.equals(entry.get("date")) && "Pull Workout".equals(entry.get("workout_type"))) {
                    request.setAttribute("error", "You have already completed the Pull Workout for today.");
                    request.getRequestDispatcher("/WEB-INF/pages/monday.jsp").forward(request, response);
                    LOGGER.warning("User " + username + " attempted to complete Pull Workout again on " + todayDate);
                    return; // Stop further processing
                }
            }
        }

            if ("completeWorkout".equals(action) || "saveProgress".equals(action)) {
            completeWorkout(request, response, username);
            } else {
                LOGGER.warning("Invalid action received: " + action);
                request.setAttribute("error", "Invalid action");
            request.getRequestDispatcher("/WEB-INF/pages/monday.jsp").forward(request, response);
        }
    }

    /**
     * Marks the workout as completed and saves progress if all exercises are completed.
     * Validates each exercise parameter from the request.
     * Forwards back to the Monday page with error if not all exercises are completed.
     * Redirects to /monday after saving progress.
     * 
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @param username Username of the logged-in user
     * @throws ServletException
     * @throws IOException
     */
    private void completeWorkout(HttpServletRequest request, HttpServletResponse response, String username) throws ServletException, IOException {
        String[] exercises = {
            "barbellSquats",
            "deadlifts",
            "assistedPullups",
            "barbellRows",
            "barbellBicepsCurls",
            "hammerCurls"
        };
        boolean allCompleted = true;
        for (String exercise : exercises) {
            String paramValue = request.getParameter(exercise);
            LOGGER.info("Checking exercise " + exercise + ": parameter value = " + (paramValue != null ? paramValue : "null"));
            if (!"completed".equals(paramValue)) {
                allCompleted = false;
                LOGGER.warning("Exercise " + exercise + " not completed (value: " + (paramValue != null ? paramValue : "null") + ")");
                break;
            }
        }
        if (!allCompleted) {
            request.setAttribute("error", "Please complete all exercises before saving progress.");
            request.getRequestDispatcher("/WEB-INF/pages/monday.jsp").forward(request, response);
            return;
        }
        saveProgress(request, username);
        LOGGER.info("Workout completed/progress saved for user: " + username + ", redirecting to /monday");
        response.sendRedirect(request.getContextPath() + "/monday");
    }

    /**
     * Saves the progress of checked exercises for the user in the session.
     * Initializes progress list if absent.
     * Adds a new progress entry containing username, date, workout type, and completed exercises.
     * 
     * @param request  HttpServletRequest object
     * @param username Username of the logged-in user
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

        // Create new progress entry
        Map<String, Object> progressEntry = new HashMap<>();
        progressEntry.put("username", username);
        progressEntry.put("date", LocalDate.now().toString());
        progressEntry.put("workout_type", "Pull Workout"); // Changed to Pull Workout for Monday
        
        List<String> completedExercises = new ArrayList<>();
        String[] exercises = {
            "barbellSquats",
            "deadlifts", 
            "assistedPullups",
            "barbellRows",
            "barbellBicepsCurls",
            "hammerCurls"
        };
         // Collect completed exercises from request parameters
        String[] jspExercises = {
            "barbellSquats",
            "deadlifts", 
            "assistedPullups",
            "barbellRows",
            "barbellBicepsCurls",
            "hammerCurls"
        };

        for (String exercise : jspExercises) {
             if ("completed".equals(request.getParameter(exercise))) {
                 completedExercises.add(exercise);
             }
        }
        progressEntry.put("completed_exercises", completedExercises);

        // Add new entry to list
        progressList.add(progressEntry);
        LOGGER.info("Added new progress entry for user: " + username + ", date: " + progressEntry.get("date") + ", completed: " + completedExercises.size() + " exercises.");
        LOGGER.info("Current progress list size in session for user: " + username + ", size: " + progressList.size());
    }

    /**
     * Lists all session attributes as a debug string.
     * Useful for debugging session state.
     * 
     * @param request HttpServletRequest object
     * @return String containing session attribute names and values
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
