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
 * Servlet implementation class SundayController
 */
@WebServlet(urlPatterns = { "/sunday" })
public class SundayController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(SundayController.class.getName());
    private static final String SESSION_PROGRESS_KEY = "sundayWorkoutProgress";

    public SundayController() {
        super();
    }

    /**
     * Handles GET requests to display the Sunday workout page.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Received GET request for /sunday, Session ID: " + 
            (request.getSession(false) != null ? request.getSession(false).getId() : "No session"));

        String username = (String) SessionUtil.getAttribute(request, "username");
        if (username == null) {
            LOGGER.warning("No username in session, redirecting to login");
            response.sendRedirect(request.getContextPath() + "/login?redirect=/sunday");
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

        request.getRequestDispatcher("/WEB-INF/pages/sunday.jsp").forward(request, response);
    }

    /**
     * Handles POST requests for completing workouts.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String username = (String) SessionUtil.getAttribute(request, "username");

        if (username == null) {
            LOGGER.warning("No username in session for POST, redirecting to login");
            response.sendRedirect(request.getContextPath() + "/login?redirect=/sunday");
            return;
        }

        if ("completeWorkout".equals(action)) {
            completeWorkout(request, response, username);
        }
    }

    private void completeWorkout(HttpServletRequest request, HttpServletResponse response, String username) 
            throws ServletException, IOException {
        // Check if workout already completed today
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> progressList = (List<Map<String, Object>>) session.getAttribute(SESSION_PROGRESS_KEY);
        String today = LocalDate.now().toString();
        
        if (progressList != null) {
            boolean alreadyCompletedToday = progressList.stream()
                .anyMatch(entry -> today.equals(entry.get("date")));
            
            if (alreadyCompletedToday) {
                LOGGER.warning("User " + username + " attempted to complete workout multiple times on " + today);
                request.setAttribute("error", "You have already completed today's workout!");
                request.getRequestDispatcher("/WEB-INF/pages/sunday.jsp").forward(request, response);
                return;
            }
        }

        String[] exercises = {"benchPress", "inclineDbPress", "machineFly", "tricepsPushdown", "skullCrushers", "latRaises", "dbShoulderPress"};
        boolean allCompleted = true;
        for (String exercise : exercises) {
            if (!"on".equals(request.getParameter(exercise))) {
                allCompleted = false;
                break;
            }
        }
        if (!allCompleted) {
            request.setAttribute("error", "Please complete all exercises before saving progress.");
            request.getRequestDispatcher("/WEB-INF/pages/sunday.jsp").forward(request, response);
            return;
        }

        saveProgress(request, username);
        LOGGER.info("Workout completed/progress saved for user: " + username + ", redirecting to /sunday");
        response.sendRedirect(request.getContextPath() + "/sunday");
    }

    @SuppressWarnings("unchecked")
    private void saveProgress(HttpServletRequest request, String username) {
        HttpSession session = request.getSession();
        List<Map<String, Object>> progressList = (List<Map<String, Object>>) session.getAttribute(SESSION_PROGRESS_KEY);
        if (progressList == null) {
            // This should ideally not happen if doGet is visited first, but as a fallback:
            progressList = new ArrayList<>();
            session.setAttribute(SESSION_PROGRESS_KEY, progressList);
             LOGGER.warning("Progress list was null in saveProgress, initialized a new one for user: " + username);
        }

        // Create new progress entry
        Map<String, Object> progressEntry = new HashMap<>();
        progressEntry.put("username", username);
        progressEntry.put("date", LocalDate.now().toString());
        progressEntry.put("workout_type", "Push Workout");
        
        List<String> completedExercises = new ArrayList<>();
        String[] exercises = {"benchPress", "inclineDbPress", "machineFly", "tricepsPushdown", "skullCrushers", "latRaises", "dbShoulderPress"};
        for (String exercise : exercises) {
            if ("on".equals(request.getParameter(exercise))) {
                completedExercises.add(exercise);
            }
        }
        progressEntry.put("completed_exercises", completedExercises);

        // Add new entry to list
        progressList.add(progressEntry);
        LOGGER.info("Added new progress entry for user: " + username + ", date: " + progressEntry.get("date") + ", completed: " + completedExercises.size() + " exercises.");
        LOGGER.info("Current progress list size in session for user: " + username + ", size: " + progressList.size());
    }

    private String listSessionAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "No session";
        }
        StringBuilder attributes = new StringBuilder();
        session.getAttributeNames().asIterator().forEachRemaining(name -> 
            attributes.append(name).append("=").append(session.getAttribute(name)).append(", "));
        return attributes.length() > 0 ? attributes.substring(0, attributes.length() - 2) : "No attributes";
    }
}