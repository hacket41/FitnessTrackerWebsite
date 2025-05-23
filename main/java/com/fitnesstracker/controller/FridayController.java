package com.fitnesstracker.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.fitnesstracker.util.SessionUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

/**
 * Servlet implementation class FridayController
 * 
 * Handles requests related to Friday's workout activities,
 * including displaying progress and recording workout completions.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/friday" })
public class FridayController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /** Logger for logging info and warnings */
    private static final Logger LOGGER = Logger.getLogger(FridayController.class.getName());

    /** Session attribute key for storing Friday workout progress */
    private static final String SESSION_PROGRESS_KEY = "fridayWorkoutProgress"; // Unique key for Friday

    /**
     * Default constructor.
     */
    public FridayController() {
        super();
    }

    /**
     * Handles GET requests to display the Friday workout page.
     * Retrieves user's workout progress from session and forwards to JSP.
     * Redirects to login if user is not authenticated.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Received GET request for /friday, Session ID: " + 
            (request.getSession(false) != null ? request.getSession(false).getId() : "No session"));
        
        String username = (String) SessionUtil.getAttribute(request, "username");
        if (username == null) {
            LOGGER.warning("No username in session, redirecting to login");
            response.sendRedirect(request.getContextPath() + "/login?redirect=/friday");
            return;
        }

        // Get user's progress list from session
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> userProgress = (List<Map<String, Object>>) session.getAttribute(SESSION_PROGRESS_KEY);

        // Initialize progress list if not present
        if (userProgress == null) {
            userProgress = new ArrayList<>();
            session.setAttribute(SESSION_PROGRESS_KEY, userProgress);
            LOGGER.info("Initialized empty progress list in session for user: " + username);
        } else {
            LOGGER.info("Retrieved progress list from session for user: " + username + ", size: " + userProgress.size());
        }

        // Set progress list as request attribute for JSP rendering
        request.setAttribute("userProgress", userProgress);
        LOGGER.info("Set userProgress attribute in request for user: " + username + ", size: " + userProgress.size());

        // Forward request to Friday workout JSP page
        request.getRequestDispatcher("/WEB-INF/pages/friday.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for completing workouts.
     * Validates user session and workout completion state.
     * Records workout progress or returns error on invalid input.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String username = (String) SessionUtil.getAttribute(request, "username");

        if (username == null) {
            LOGGER.warning("No username in session for POST, redirecting to login");
            response.sendRedirect(request.getContextPath() + "/login?redirect=/friday");
            return;
        }

        // Retrieve progress list from session
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> progressList = (List<Map<String, Object>>) session.getAttribute(SESSION_PROGRESS_KEY);

        // Check if workout for today already completed by user
        if (progressList != null) {
            String todayDate = LocalDate.now().toString();
            for (Map<String, Object> entry : progressList) {
                if (username.equals(entry.get("username")) && todayDate.equals(entry.get("date")) && "Cardio Workout".equals(entry.get("workout_type"))) {
                    request.setAttribute("error", "You have already completed the Cardio Workout for today.");
                    request.getRequestDispatcher("/WEB-INF/pages/friday.jsp").forward(request, response);
                    LOGGER.warning("User " + username + " attempted to complete Cardio Workout again on " + todayDate);
                    return;
                }
            }
        }

        // Process action parameter
        if ("completeWorkout".equals(action)) {
            completeWorkout(request, response, username);
        } else {
            LOGGER.warning("Invalid action received: " + action);
            request.setAttribute("error", "Invalid action");
            request.getRequestDispatcher("/WEB-INF/pages/friday.jsp").forward(request, response);
        }
    }

    /**
     * Marks the workout as completed if all exercises are checked.
     * Saves the workout progress and redirects back to the Friday page.
     * If not all exercises completed, returns an error message to JSP.
     * 
     * @param request  the HttpServletRequest containing form parameters
     * @param response the HttpServletResponse used for redirecting or forwarding
     * @param username the username of the currently logged-in user
     */
    private void completeWorkout(HttpServletRequest request, HttpServletResponse response, String username)
            throws ServletException, IOException {
        String[] exercises = {"running", "jumpingJacks", "cycling", "highKnees", "burpees"};
        boolean allCompleted = true;

        // Check if all exercises are marked completed (parameter value "on")
        for (String exercise : exercises) {
            String paramValue = request.getParameter(exercise);
            LOGGER.info("Checking exercise " + exercise + ": parameter value = " + (paramValue != null ? paramValue : "null"));
            if (!"on".equals(paramValue)) {
                allCompleted = false;
                LOGGER.warning("Exercise " + exercise + " not completed (value: " + (paramValue != null ? paramValue : "null") + ")");
                break;
            }
        }

        // If any exercise not completed, set error and forward back to JSP
        if (!allCompleted) {
            request.setAttribute("error", "Please complete all exercises before saving progress.");
            request.getRequestDispatcher("/WEB-INF/pages/friday.jsp").forward(request, response);
            return;
        }

        // Save progress and redirect
        saveProgress(request, username);
        LOGGER.info("Workout completed/progress saved for user: " + username + ", redirecting to /friday");
        response.sendRedirect(request.getContextPath() + "/friday");
    }

    /**
     * Saves the workout progress for the user into session attribute list.
     * Creates a new progress entry with username, date, workout type, and completed exercises.
     * 
     * @param request  the HttpServletRequest used to access session and parameters
     * @param username the username of the logged-in user
     */
    @SuppressWarnings("unchecked")
    private void saveProgress(HttpServletRequest request, String username) {
        HttpSession session = request.getSession();
        List<Map<String, Object>> progressList = (List<Map<String, Object>>) session.getAttribute(SESSION_PROGRESS_KEY);

        // Initialize progress list if not present
        if (progressList == null) {
            progressList = new ArrayList<>();
            session.setAttribute(SESSION_PROGRESS_KEY, progressList);
            LOGGER.warning("Progress list was null in saveProgress, initialized a new one for user: " + username);
        }

        // Create new progress entry map
        Map<String, Object> progressEntry = new HashMap<>();
        progressEntry.put("username", username);
        progressEntry.put("date", LocalDate.now().toString());
        progressEntry.put("workout_type", "Cardio Workout");

        // Collect completed exercises from request parameters
        List<String> completedExercises = new ArrayList<>();
        String[] exercises = {"running", "jumpingJacks", "cycling", "highKnees", "burpees"};
        for (String exercise : exercises) {
            if ("on".equals(request.getParameter(exercise))) {
                completedExercises.add(exercise);
            }
        }
        progressEntry.put("completed_exercises", completedExercises);

        // Add new progress entry to session list
        progressList.add(progressEntry);

        LOGGER.info("Added new progress entry for user: " + username + ", date: " + progressEntry.get("date") + ", completed: " + completedExercises.size() + " exercises.");
        LOGGER.info("Current progress list size in session for user: " + username + ", size: " + progressList.size());
    }

    /**
     * Utility method to list all session attributes for debugging purposes.
     * 
     * @param request the HttpServletRequest containing the session
     * @return String representation of all session attributes and their values
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
