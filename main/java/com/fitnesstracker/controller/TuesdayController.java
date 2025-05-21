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
import java.util.logging.Logger;
import java.util.Enumeration;
import java.util.*;

/**
 * Servlet implementation class TuesdayController
 * Handles HTTP requests related to Tuesday's Leg Workout routine.
 * Manages user session validation, workout completion tracking, and progress storage.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/tuesday" })
public class TuesdayController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Logger for debugging and tracking servlet operations
    private static final Logger LOGGER = Logger.getLogger(TuesdayController.class.getName());

    // Key used to store and retrieve Tuesday workout progress in session
    private static final String SESSION_PROGRESS_KEY = "tuesdayWorkoutProgress";

    /**
     * Default constructor.
     */
    public TuesdayController() {
        super();
    }

    /**
     * Handles GET requests to /tuesday.
     * Validates user session, retrieves or initializes workout progress,
     * and forwards to the Tuesday workout JSP view.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Received GET request for /tuesday, Session ID: " + 
            (request.getSession(false) != null ? request.getSession(false).getId() : "No session"));

        String username = (String) SessionUtil.getAttribute(request, "username");
        if (username == null) {
            LOGGER.warning("No username in session, redirecting to login");
            response.sendRedirect(request.getContextPath() + "/login?redirect=/tuesday");
            return;
        }

        // Retrieve progress from session or initialize new list
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

        // Set progress as request attribute for rendering in JSP
        request.setAttribute("userProgress", userProgress);
        LOGGER.info("Set userProgress attribute in request for user: " + username + ", size: " + userProgress.size());

        request.getRequestDispatcher("/WEB-INF/pages/tuesday.jsp").forward(request, response);
    }

    /**
     * Handles POST requests to /tuesday.
     * Validates session, checks for duplicate completion, and processes workout actions.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String username = (String) SessionUtil.getAttribute(request, "username");

        if (username == null) {
            LOGGER.warning("No username in session for POST, redirecting to login");
            response.sendRedirect(request.getContextPath() + "/login?redirect=/tuesday");
            return;
        }

        // Check for duplicate workout entry for current date
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> progressList = (List<Map<String, Object>>) session.getAttribute(SESSION_PROGRESS_KEY);

        if (progressList != null) {
            String todayDate = LocalDate.now().toString();
            for (Map<String, Object> entry : progressList) {
                if (username.equals(entry.get("username")) && todayDate.equals(entry.get("date")) && "Leg Workout".equals(entry.get("workout_type"))) {
                    request.setAttribute("error", "You have already completed the Leg Workout for today.");
                    request.getRequestDispatcher("/WEB-INF/pages/tuesday.jsp").forward(request, response);
                    LOGGER.warning("User " + username + " attempted to complete Leg Workout again on " + todayDate);
                    return;
                }
            }
        }

        // Route based on action parameter
        if ("completeWorkout".equals(action) || "saveProgress".equals(action)) {
            completeWorkout(request, response, username);
        } else {
            LOGGER.warning("Invalid action received: " + action);
            request.setAttribute("error", "Invalid action");
            request.getRequestDispatcher("/WEB-INF/pages/tuesday.jsp").forward(request, response);
        }
    }

    /**
     * Validates workout form submission to ensure all exercises are completed.
     * If valid, saves progress; otherwise, shows error message.
     *
     * @param request  the HttpServletRequest containing form data
     * @param response the HttpServletResponse for redirection
     * @param username the logged-in username from session
     */
    private void completeWorkout(HttpServletRequest request, HttpServletResponse response, String username)
            throws ServletException, IOException {
        String[] exercises = {"squats", "legPress", "lunges", "legExtensions", "legCurls", "calfRaises"};
        boolean allCompleted = true;

        for (String exercise : exercises) {
            String paramValue = request.getParameter(exercise);
            LOGGER.info("Checking exercise " + exercise + ": parameter value = " + (paramValue != null ? paramValue : "null"));
            if (!"on".equals(paramValue)) {
                allCompleted = false;
                LOGGER.warning("Exercise " + exercise + " not completed (value: " + (paramValue != null ? paramValue : "null") + ")");
                break;
            }
        }

        if (!allCompleted) {
            request.setAttribute("error", "Please complete all exercises before saving progress.");
            request.getRequestDispatcher("/WEB-INF/pages/tuesday.jsp").forward(request, response);
            return;
        }

        saveProgress(request, username);
        LOGGER.info("Workout completed/progress saved for user: " + username + ", redirecting to /tuesday");
        response.sendRedirect(request.getContextPath() + "/tuesday");
    }

    /**
     * Saves workout completion details to the session for the given user.
     * Stores the date, workout type, and completed exercises.
     *
     * @param request  the HttpServletRequest containing form data
     * @param username the username whose progress is being saved
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

        // Create and populate new progress entry
        Map<String, Object> progressEntry = new HashMap<>();
        progressEntry.put("username", username);
        progressEntry.put("date", LocalDate.now().toString());
        progressEntry.put("workout_type", "Leg Workout");

        List<String> completedExercises = new ArrayList<>();
        String[] exercises = {"squats", "legPress", "lunges", "legExtensions", "legCurls", "calfRaises"};
        for (String exercise : exercises) {
            if ("on".equals(request.getParameter(exercise))) {
                completedExercises.add(exercise);
            }
        }
        progressEntry.put("completed_exercises", completedExercises);

        // Store new progress entry in session list
        progressList.add(progressEntry);
        LOGGER.info("Added new progress entry for user: " + username + ", date: " + progressEntry.get("date") + ", completed: " + completedExercises.size() + " exercises.");
        LOGGER.info("Current progress list size in session for user: " + username + ", size: " + progressList.size());
    }

    /**
     * Utility method to list all session attributes as a string (used for debugging).
     *
     * @param request the HttpServletRequest from which the session is obtained
     * @return a string representation of all session attributes
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
