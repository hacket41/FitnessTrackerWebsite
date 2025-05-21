package com.fitnesstracker.controller;

import com.fitnesstracker.config.DBConfig;
import com.fitnesstracker.model.UploadedWorkout;
import com.fitnesstracker.service.WorkoutUploadService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;

/**
 * Servlet to handle editing of uploaded workouts.
 * Processes GET requests to load a workout by ID and forward to the edit workout JSP page.
 * POST requests delegate to GET as form submission is not handled here.
 */
@WebServlet("/editworkout")
public class EditWorkoutController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public EditWorkoutController() {
        super();
    }

    /**
     * Handles GET requests to retrieve a workout by ID, sets it as a request attribute,
     * and forwards to the edit workout JSP page.
     * Redirects to admin content with error messages on invalid or missing IDs or server errors.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");

        // Validate workout ID parameter
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admincontent?error=Invalid+Workout+ID");
            return;
        }

        try {
            int workoutId = Integer.parseInt(idParam);
            try (Connection conn = DBConfig.getDbConnection()) {
                WorkoutUploadService workoutService = new WorkoutUploadService(conn);
                UploadedWorkout workout = workoutService.getWorkoutById(workoutId);

                // If workout not found, redirect with error message
                if (workout == null) {
                    response.sendRedirect(request.getContextPath() + "/admincontent?error=Workout+not+found");
                    return;
                }

                // Set workout as request attribute for editing
                request.setAttribute("editWorkout", workout);
                request.getRequestDispatcher("/WEB-INF/pages/editworkout.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            // Redirect if workout ID is not a valid number
            response.sendRedirect(request.getContextPath() + "/admincontent?error=Invalid+Workout+ID+Format");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admincontent?error=Server+Error");
        }
    }

    /**
     * Handles POST requests by delegating to doGet since form submission is not used here.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); 
    }
}
